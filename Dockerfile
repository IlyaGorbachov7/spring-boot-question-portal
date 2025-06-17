FROM openjdk:21

# Устанавливаем рабочую директорию По умолчанию если не указывать WORKDIR будет /
WORKDIR /
ADD /target/question-portal-back.jar backend.jar
VOLUME app-storage-dev: store
ENTRYPOINT ["java"]
CMD ["-jar", "backend.jar"]