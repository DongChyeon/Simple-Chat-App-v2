import room_model from "../models/room_model.js";

export const createRoom = async (req, res) => {
    var name = req.body.name;
    var room_img = req.file.path;
    var user = req.body.user;

    var room = room_model({ 'name' : name, 'room_img' : room_img, 'users' : [ user ] });
    await room.save();

    if (room) {
        console.log(`방 ${room._id} 추가 성공`);
        res.status(201).json({ message : `방 ${room._id} 추가에 성공했습니다.` });
    } else {
        console.log(`방 ${room._id} 추가 실패`);
        res.status(400).json({ message : `방 ${room._id} 추가에 실패했습니다.` });
    }
};

export const enterRoom = async (req, res) => {
    var room_id = req.params.room_id;
    var user_id = req.params.user_id;

    const room = await room_model.findById(room_id).exec();;
    room.users.push(user_id);
    await room.save();

    if (room) {
        console.log(`유저 ${user_id} 가 방 ${room_id} 에 입장 성공`);
        res.status(201).json({ message : `유저 ${user_id} 가 방 ${room_id} 에 들어왔습니다.` });
    } else {
        console.log(`유저 ${user_id} 가 방 ${room_id} 에 입장 실패`);
        res.status(400).json({ message : `유저 ${user_id} 가 방 ${room_id} 에 들어오는데 실패했습니다.` });
    }
};

export const leaveRoom = async (req, res) => {
    var room_id = req.params.room_id;
    var user_id = req.params.user_id;

    const room = await room_model.findById(room_id);
    room.users.pull(user_id);

    // 방에 더 이상 유저가 남아있지 않다면 삭제
    if (room.users.length == 0) {
        await deleteRoom(room_id, res);
    } else {
        await room.save();
        if (room) {
            console.log(`유저 ${user_id} 가 방 ${room_id} 에서 퇴장 성공`);
            res.status(201).json({ message : `유저 ${user_id}가 방 ${room_id}에서 나갔습니다.` });
        } else {
            console.log(`유저 ${user_id} 가 방 ${room_id} 에서 퇴장 실패`);
            res.status(400).json({ message : `유저 ${user_id}가 방 ${room_id}에서 나가는데 실패했습니다.` });
        }
    }
};

const deleteRoom = async (room_id, res) => {
    return await room_model.deleteOne({ _id : room_id }).exec((err, d) => {
        if (err) res.status(400).json(`방 ${room_id} 삭제 실패`);
        if (d.acknowledged && d.deletedCount == 1) {
            console.log(`방 ${room_id} 삭제 성공`);
            res.status(201).json({ message : `방 ${room_id} 에 인원이 없어 삭제되었습니다.` });
        } else {
            console.log(`방 ${room_id} 가 이미 존재하지 않습니다.`);
            res.status(400).json({ message : `방 ${room_id} 가 이미 존재하지 않습니다.` });
        }
    });
};

export const getRoomList = async (req, res) => {
    const rooms = await room_model.find().exec();

    try {
        const obj = JSON.parse(JSON.stringify(rooms));
        console.log('방 목록 불러오기 성공');
        res.status(200).json({ 'rooms' : obj, 'message' : '채팅방 목록을 성공적으로 불러왔습니다.' });
    } catch(err) {
        console.log('방 목록 불러오기 실패');
        res.status(401).json({ 'rooms' : [], 'message' : err });
    }
};