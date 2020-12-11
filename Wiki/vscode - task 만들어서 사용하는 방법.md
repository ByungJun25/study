# vscode에서 task 만들어 사용하는 방법
참고: [vscode - tasks](https://code.visualstudio.com/docs/editor/tasks)

> task는 추후 launch.json에서 preLaunchTask를 통해 사용할 수도 있음.

## task 만드는 법

1. 템플릿에서 만들기
    1. `ctrl` + `shift` + `p` 누른후, `Tasks: Configure Task` 선택.
    2. `Crete tasks.json file from template` 선택.
    3. 원하는 템플릿 선택.
    4. `tasks.json` 생성 후, 다시 `Tasks: Configure Task` 선택하면, 자동으로 프로젝트에 맞게 몇가지 템플릿을 추천해줌.

2. 바로 만들기
    1. `.vscode` 폴더 아래에 `tasks.json` 생성.
    2. `ctrl` + `shift` + `p` 누른후, `Tasks: Configure Task` 선택.
    3. 원하는 설정 선택.

3. custom task를 위한 properties
    1. label: task의 이름 설정.
    2. type: task의 타입 설정. 커스텀 task를 위해서는 `shell`이나 `process` 중 선택.
    3. command: 실제 사용될 명령어 설정.
    4. group: 어떤 그룹에 task를 분류할지 설정.
    5. presentation: task의 output을 어떻게 다룰건지 설정.

## task 사용법

1. `ctrl` + `shift` + `p` 누른후, `Tasks: Run Task` 선택.
2. 자신이 원하는 task 선택.