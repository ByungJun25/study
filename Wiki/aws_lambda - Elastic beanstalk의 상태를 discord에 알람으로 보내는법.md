# AWS의 Elastic beanstalk의 상태 정보를 Discord의 Webhook을 이용하여 알람설정하는 법

### 개요
AWS의 Elastic beanstalk으로 운영중, 환경 상태(OK, Warning, Degraded, Severe) 변화에 따라 디스코드로 메시지를 받을 필요가 생겼고, 이를 구성하는 방법을 설명함.

#### 시스템 구조

Elastic beanstalk -> Amazon SNS -> Lambda -> Discord

#### 구현

1. Elastic Beanstalk를 AWS SNS의 주제로 등록을 해야합니다. 이는 Amazon SNS에서 직접 설정할 수 있지만, 쉽게하기 위해 Elastic Beanstalk의 알람 설정을 통해 쉽게 주제를 등록할 수 있습니다.
    1. Elastic Beanstalk -> 환경 -> 구성 -> 알림으로 이동 
    2. 이메일 설정   
       : 이메일을 설정하게되면 AWS가 자동으로 Amazon SNS에 주제를 설정해줍니다. 이렇게 설정된 주제에 이제 저희는 직접 만든 Lambda를 연결할 것입니다.

2.  Lambda 함수 세팅  
    : 여기서는 javascript를 이용하여 간단하게 코드를 구현하겠습니다. 다음과 같습니다. 해당 코드는 axios를 사용하는데, AWS Lambda에서 해당 모듈을 사용하기 위해선, 따로 업로드를 해줘야합니다. 이는 뒤에서 다시 설명하겠습니다.

    ```javascript
    const axios = require('axios');

    const webhookUrl = process.env.DISCORD_WEBHOOK_URL || '';
    const errorKeywords = process.env.ERROR_KEYWORDS || 'to Degraded,to Severe,to Failed,to Unhealthy,to Stopped,to Terminated,to Error,to Critical,to Down';
    const warningKeywords = process.env.WARNING_KEYWORDS || 'to Warning';

    exports.handler = async (event, context) => {
        try {
            if (!webhookUrl) {
                throw new Error('Discord webhook URL is not set');
            }

            const payload = buildDiscordMsg(event);

            // Send the payload to the Discord webhook using Axios
            await axios.post(webhookUrl, payload);
        
            return {
                statusCode: 200,
                body: 'Message sent to Discord successfully'
            };
        } catch (error) {
            console.error('Error sending webhook message:', error.message);
            return {
                statusCode: 500,
                body: 'Error sending webhook message'
            };
        }
    };

    // Elastic beanstalk가 보내는 데이터를 파싱하는 메소드입니다.
    // 데이터는 Message라는 부분에 담겨오는데 JSON포맷이 아닌 일반 string 포맷으로 각 데이터는 개행문자로만 분류되도록 되어있습니다.
    // 따라서 개행 문자를 통해 각 데이터를 나누고 다시 파싱하는 과정이 해당 메소드에 구현되어있습니다.
    const parseSnsMessage = (message) => {
        try {
            const parts = message.split('\n');
            const data = {
                Environment: "",
                Timestamp: "",
                Message: ""
            };

            parts.forEach((part) => {
                part = part.trim();
                
                if (!part) {
                    return data;
                }

                if (!part.includes(":")) {
                    return data;
                }

                let [key, value] = part.split(":");
                key = key.trim();
                value = value.trim();
                
                if (!key || !value) {
                    return;
                }

                Object.assign(data, { [key]: value });
            });

            return data;
        } catch (error) {
            console.log("parseSnsMsg error: " + error);
            return null;
        }
    };

    // 디스코드에 보낼 메시지를 만드는 메소드입니다.
    // https://message.style/app/editor -> 해당 사이트에서 좀 더 상세하게 메시지를 구성해볼수 있습니다.
    const buildDiscordMsg = (event) => {
        const msg = event.Records[0].Sns.Message; // Sns.Message 부분이 Elastic beanstalk가 보내는 데이터부분입니다.
        const data = parseSnsMessage(msg);

        if (!data || !data.Environment || !data.Timestamp || !data.Message) {
            console.log("The event message is invalid: " + JSON.stringify(msg));
            return null;
        }

        return {
            username: "ALARM_BOT",
            content: "Elastic beanstalk 서버의 상태가 변경되었습니다. 확인해주세요.",
            embeds: [
            {
                title: "Environment: " + data.Environment,
                description: highlightMessage(data.Message),
                color: setColor(data.Message),
            }
            ]
        }
    }

    // 메세지의 첫 문장을 bold처리하는 메소드입니다.
    const highlightMessage = (msg) => {
        const sentences = msg.split(".");
        sentences[0] = `**${sentences[0]}**`;
        return sentences.join(".");
    };

    // 각 상태변화에 따라 디스코드의 embeds의 색상을 변경하기 위한 메소드입니다.
    const setColor = (msg) => {
        const arrErrorKeys = errorKeywords.split(",");
        const arrWarningKeys = warningKeywords.split(",");

        for (const key of arrErrorKeys) {
            if (msg.includes(key)) {
            return 15548997; // 빨강
            }
        }

        for (const key of arrWarningKeys) {
            if (msg.includes(key)) {
            return 16776960; // 노랑
            }
        }

        return 5763719; // 초록
    }
    ```

3. 앞서 만들어진 SNS의 주제에 구현한 Lambda를 구독으로 설정합니다. 주제ARN은 앞서 만든것으로 선택하고, 프로토콜에는 `AWS Lambda`를 선택한 후, 엔드포인트에는 만든 Lambda의 ARN값을 입력합니다.

#### 추가 설명
1. AWS Lambda에 javascript 모듈을 업로드하는 방법  
    : 이는 AWS Lambda의 Layer를 이용하면 됩니다. 앞서 우리는 axios를 사용하므로 이를 예시로 들어보겠습니다.

    1. 우선 로컬 환경에서 다음의 명령어를 통해 axios모듈을 설치하고 이를 zip파일을 만듭니다.
        ```bash
        ## 폴더를 생성하는데 이름을 꼭 nodejs로 해두어야합니다. 다른 이름으로 설정할 시, AWS Lambda에서 이를 이용하지 않습니다.
        mkdir nodejs

        ## 해당 폴더로 이동
        cd nodejs

        ## axios 설치
        npm i axios

        ## package-lock 파일은 불필요하므로 삭제합니다
        rm -rf package-lock.json

        ## 상위 폴더로 이동
        cd ..

        ## zip 파일을 만듭니다.
        zip -r axios.zip nodejs
        ```

    2. AWS Lambda메뉴중에서 Layers 메뉴로 이동합니다.
    3. Create Layer를 누릅니다.
    4. 이름과 설명을 원하시는 값으로 설정한 후, 이전에 만든 zip 파일을 업로드합니다.
    5. 생성된 layer를 원하는 lambda에 추가합니다.
