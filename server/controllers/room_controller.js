import room_model from "../models/room_model.js";

export const createRoom = async (req, res) => {
    try {
        const name = req.body.name;
        const room_img = req.file.path;
        const uid = req.body.uid;

        const room = await room_model.createRoom(name, room_img, uid);
        console.log(`방 ${room._id} 추가 성공`);
        return res.status(201).json({ message : `방 ${room._id} 추가에 성공했습니다.` });
    } catch (err) {
        console.log(`방 추가 에러`, err);
        return res.status(500).json({ message : err });
    }
}

export const enterRoom = async (req, res) => {
    try {
        const id = req.params.id;
        const uid = req.params.uid;

        const room = await room_model.getRoomById(id);
        room.members.push(uid);
        await room.save();

        console.log(`유저 ${uid} 가 방 ${id} 에 입장 성공`);
        return res.status(200).json({ message : `유저 ${uid} 가 방 ${id} 에 들어왔습니다.` });
    } catch (err) {
        console.log(`유저 ${uid} 가 방 ${id} 에 입장 실패`, err);
        return res.status(500).json({ message : `유저 ${uid} 가 방 ${id} 에 들어오는데 실패했습니다.` });
    }
}

export const leaveRoom = async (req, res) => {
    try {
        const id = req.params.id;
        const uid = req.params.uid;

        const room = await room_model.getRoomById(id);
        room.members.pull(uid);

        // 방에 더 이상 유저가 남아있지 않다면 삭제
        if (room.members.length == 0) {
            await room_model.deleteRoomById(id);
            console.log(`방 ${id} 에 유저가 남아있지 않아 삭제`);
            return res.status(200).json({ message : `방 ${id} 가 삭제되었습니다.` });
        } else {
            await room.save();
            console.log(`유저 ${uid} 가 방 ${id} 에서 퇴장 성공`);
            return res.status(200).json({ message : `유저 ${uid} 가 방 ${id} 에서 나갔습니다.` });
        }
    } catch (err) {
        console.log(`유저 ${uid} 가 방 ${id} 에서 퇴장 실패`, err);
        return res.status(500).json({ message : err });
    }
}

export const getAllRooms = async (req, res) => {
    try {
        const rooms = room_model.getRooms();
        console.log('방 목록 불러오기 성공');
        const obj = JSON.parse(JSON.stringify(rooms));
        return res.status(200).json({ 'rooms' : obj, 'message' : '채팅방 목록을 성공적으로 불러왔습니다.' });
    } catch (err) {
        console.log('방 목록 불러오기 에러', err)
        return res.status(500).json({ 'message' : err });
    }
}

export const getRoomsByUid = async (req, res) => {
    const uid = req.params.uid;

    try {
        const rooms = room_model.getRoomsByUid(uid);
        console.log('방 목록 불러오기 성공');
        const obj = JSON.parse(JSON.stringify(rooms));
        return res.status(200).json({ 'rooms' : obj, 'message' : '채팅방 목록을 성공적으로 불러왔습니다.' });
    } catch (err) {
        console.log('방 목록 불러오기 에러', err)
        return res.status(500).json({ 'message' : err });
    }
}