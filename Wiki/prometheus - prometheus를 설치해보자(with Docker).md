# Docker를 이용하여 Prometheus 설치하는 방법

Docker를 이용하여 Prometheus를 설치하고 Grafana까지 연동해보자.  
(설치만 해보는 것이라서 실업무에 적용할때는 더 공부해야함.)
> 설치는 [Prometheus - Document](https://prometheus.io/docs/prometheus/latest/installation/#using-docker)를 참고하였습니다.

1. `docker-compose.yml`을 다음과 같이 작성한다.

```yml
version: '3.7'
services:
prometheus:
    image: prom/prometheus
    container_name: prometheus
    command:
    - '--web.listen-address=0.0.0.0:9090' ## listen 주소 설정
    - '--config.file=/etc/prometheus/prometheus.yml' ## prometheus 설정파일 경로 설정
    - '--storage.tsdb.path=/prometheus'
    - '--web.console.libraries=/etc/prometheus/console_libraries'
    - '--web.console.templates=/etc/prometheus/consoles'
    - '--storage.tsdb.retention.time=200h'
    - '--web.enable-lifecycle'
    volumes:
    - ./data/prometheus:/etc/prometheus
    - ./data/prometheus/data:/prometheus
    ports:
    - 9090:9090
    networks:
    - prometheus-network

grafana:
    container_name: grafana
    image: grafana/grafana
    environment:
    - GF_SECURITY_ADMIN_USER=user1 ## 기본 유저 등록
    - GF_SECURITY_ADMIN_PASSWORD=user1
    - GF_USERS_ALLOW_SIGN_UP=false
    volumes:
    - ./data/grafana/data:/var/lib/grafana
    - ./data/grafana/provisioning:/etc/grafana/provisioning
    ports:
    - 3000:3000
    depends_on:
    - prometheus
    networks:
    - prometheus-network

networks:
prometheus-network:
```

2. Prometheus 설정 파일을 다음과 같이 작성한다. (`prometheus.yml`)  
설정파일은 [FINDA 기술블로그 - Prometheus란?](https://medium.com/finda-tech/prometheus%EB%9E%80-cf52c9a8785f)을 참고하였습니다.

```yml
# 기본적인 전역 설정 
global:
  scrape_interval:     15s # 15초마다 매트릭을 수집한다. 기본은 1분이다.
  evaluation_interval: 15s # 15초마다 매트릭을 수집한다. 기본은 1분이다.
  # 'scrpae_timeout' 이라는 설정은 기본적으로 10초로 세팅되어 있다.

# Alertmanager 설정
alerting:
  alertmanagers:
  - static_configs:
    - targets:
      # - alertmanager:9093

# 규칙을 처음 한번 로딩하고 'evaluation_interval'설정에 따라 정기적으로 규칙을 평가한다.
rule_files:
  # - "first_rules.yml"
  # - "second_rules.yml"

# 매트릭을 수집할 엔드포인트를 설정. 여기서는 Prometheus 서버 자신을 가리키는 설정을 했다.
scrape_configs:
  # 이 설정에서 수집한 타임시리즈에 'job=<job_name>'으로 잡의 이름을 설정한다.
  - job_name: 'prometheus'
    # 'metrics_path'라는 설정의 기본 값은 '/metrics'이고
    # 'scheme'라는 설정의 기본 값은 'http'이다.
    static_configs:
    - targets: ['localhost:9090']
```

3. `docker-compose` 파일을 실행한다.