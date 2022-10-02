import mongoose from 'mongoose';
import dotenv from 'dotenv';
dotenv.config();

const database = { };

database.init = () => {
    connect();
};

function connect() {
    mongoose.Promise = global.Promise;
    mongoose.connect(process.env.DB_URL, (error) => {
        if (error) {
            console.log('데이터베이스 연결에 실패했습니다.');
        }
    });

    mongoose.connection.on('error', (error) => {
        console.error('데이터베이스 연결 에러 : ', error);
    });
    mongoose.connection.on('open', () => {
        console.log('데이터베이스에 연결되었습니다. : ' + process.env.DB_URL);
    });
    mongoose.connection.on('disconnected', () => {
        console.error('데이터베이스 연결이 끊겼습니다. 다시 연결합니다.');
        connect();
    });
}

export default database;