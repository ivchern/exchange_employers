FROM ubuntu:20.04

RUN apt-get update && apt-get install -y python3.9 python3.9-dev python3.9-distutils wget
RUN wget https://bootstrap.pypa.io/get-pip.py
RUN python3.9 get-pip.py
COPY ./exchange_employers_recommendation/requirements.txt .
RUN python3.9 -m pip install -r requirements.txt
COPY ./exchange_employers_recommendation/ .
WORKDIR .
CMD ["python3.9", "main.py"]
