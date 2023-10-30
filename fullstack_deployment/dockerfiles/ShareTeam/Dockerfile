FROM node:16-alpine

WORKDIR /app
#COPY ./package.json /*.json
COPY . /app
RUN npm install --legacy-peer-deps

EXPOSE 3000
CMD ["npm", "start"]

