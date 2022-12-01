import express from 'express';
import { createServer } from 'http';
import { Server } from 'socket.io';
import bodyParser from 'body-parser';
import cookieParser from 'cookie-parser';
import dotenv from 'dotenv';
import path from 'path';
import web_socket from './utils/web_socket.js';
import database from './database.js';
import route_loader from './routes/route_loader.js';
dotenv.config();

const app = express();

// 서버 포트 설정
app.set('port', process.env.SERVER_PORT);

app.use(express.static(path.resolve())) 
app.use(bodyParser.urlencoded({ extended : true }));
app.use(bodyParser.json());
app.use(cookieParser());

// 라우팅 설정 초기화
route_loader.init(app, express.Router());

const server = createServer(app).listen(app.get('port'), () => {
    console.log('서버를 시작했습니다. : ' + app.get('port'));

    database.init();
});

global.io = new Server(server);
global.io.on('connection', web_socket.connection);