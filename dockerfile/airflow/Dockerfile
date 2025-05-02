FROM apache/airflow:2.8.1-python3.11

WORKDIR ./airflow
USER root
# 필요한 시스템 패키지 설치 (선택)
RUN apt-get update && apt-get install -y gcc

USER airflow
COPY ./airflow/requirements.txt /requirements.txt
RUN pip install --no-cache-dir -r /requirements.txt

COPY ./airflow/dags/ /opt/airflow/dags/
