import { updateUserStatus } from '../controllers/user_controller.js';

class web_socket {
    online_users = [];

    connection(client) {
        client.on('connect', (uid) => {
            this.online_users.push({
                'socketId' : client.id,
                'uid' : uid
            });
            updateUserStatus(uid, "ONLINE");
        });
        client.on('disconnect', () => {
            const uid = this.online_users.filter((user) => user.socketId === client.id).uid;
            this.online_users = this.online_users.filter((user) => user.socketId !== client.id);
            updateUserStatus(uid, "OFFLINE");
        });
        client.on('subscribe', (room, otherUid = '') => {
            this.subscribeOtheruser(room, otherUid);
            client.join(room);
        });
        client.on('unsubscribe', (room) => {
            client.leave(room);
        });
    }

    subscribeOtheruser(room, otherUid) {
        const online_usersockets = this.online_users.filter(
            (user) => user.uid === otherUid
        );
        online_usersockets.map((userInfo) => {
            const socketConn = global.io.sockets.connected(userInfo.socketId);
            if (socketConn) socketConn.join(room);
        });
    }
}

export default new web_socket();