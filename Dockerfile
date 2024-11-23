FROM ubuntu:latest
LABEL authors="juans"

ENTRYPOINT ["top", "-b"]