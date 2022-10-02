import express from 'express';
import { createServer } from 'http';
import { Server } from 'socket.io';
import bodyParser from 'body-parser';
import cookieParser from 'cookie-parser';
import dotenv from 'dotenv';
import database from './database.js';
import route_loader from './routes/route_loader.js';
dotenv.config();

const app = express();

// 서버 포트 설정
app.set('port', process.env.SERVER_PORT);

app.use(bodyParser.urlencoded({ extended : true }));
app.use(bodyParser.json());
app.use(cookieParser());

// 라우팅 설정 초기화
route_loader.init(app, express.Router());

const server = createServer(app).listen(app.get('port'), () => {
    console.log('서버를 시작했습니다. : ' + app.get('port'));

    database.init();
});

const io = new Server(server);

io.sockets.on('connection', (socket) => {
    console.log(`소켓 연결 : ${socket.id}`)

    socket.on('disconnect', () => {
        console.log(`소켓 연결 해제 : ${socket.id}`)
    })
});