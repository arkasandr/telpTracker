FROM openjdk:11 as backend
RUN apt-get update && apt-get install maven -y
WORKDIR /backend
COPY backend .
RUN mvn install
ENTRYPOINT ["java", "-jar", "target/archivePSN-1.0.jar"]

# build vue frontend static files
FROM node:lts-buster-slim AS frontend
WORKDIR /app
COPY frontend frontend/vue.config.js frontend/babel.config.js ./
RUN npm install
COPY frontend src
COPY frontend public
RUN npm run build

# final stage, nginx for static frontend
FROM nginx:latest as nginx-frontend
RUN mkdir /app
COPY --from=frontend /app/dist /app/dist
COPY nginx.conf /etc/nginx/nginx.conf

