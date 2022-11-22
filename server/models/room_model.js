import mongoose from 'mongoose';
import { v4 as uuidv4 } from 'uuid';

const roomSchema = new mongoose.Schema({
    rid : { type : String, required : true, unique : true },
    name : { type : String, required : true },
    room_img : { type : String, required : true },
    // 채팅방에 있는 유저 (uid) 들의 목록
    members : [{ type : String, required : true }]
}, {
    timestamps : true,
    collection : 'rooms'
});

roomSchema.statics.createRoom = async function (name, room_img, uid) {
    try {
        const room = await this.create({ 'rid' : uuidv4(), 'name' : name, 'room_img' : room_img, 'members' : [ uid ] });
        return room;
    } catch (err) {
        throw 'createRoom 에러 : ' + err;
    }
}

roomSchema.statics.deleteRoomById = async function (id) {
    try {
        const room = await this.deleteOne({ 'rid' : id });
        return room;
    } catch (err) {
        throw 'deleteRoomById 에러 : ' + err;
    }
}

roomSchema.statics.getRoomsByUid = async function (uid) {
    try {
        const rooms = await this.find({ 'members' : { $all : [uid] } }, { 
            _id : 0,
            rid : 1,
            name : 1,
            room_img : 1,
            members : 1
        });
        return rooms;
    } catch (err) {
        throw 'getRoomsByUid 에러 : ' + err;
    }
}

roomSchema.statics.getRoomById = async function (id) {
    try {
        const room = await this.findOne({ 'rid' : id });
        return room;
    } catch (err) {
        throw 'getRoomById 에러 : ' + err;
    }
} 

roomSchema.statics.getAllRooms = async function () {
    try {
        const rooms = await this.find({}, { 
            _id : 0,
            rid : 1,
            name : 1,
            room_img : 1,
            members : 1
        });
        return rooms;
    } catch (err) {
        throw 'getAllRooms 에러 : ' + err;
    }
}

const room_model = mongoose.model('room', roomSchema);

export default room_model;