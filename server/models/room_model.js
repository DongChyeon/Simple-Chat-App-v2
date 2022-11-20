import mongoose from 'mongoose';

const roomSchema = new mongoose.Schema({
    name : { type : String, required : true },
    room_img : { type : String, required : true },
    // 채팅방에 있는 유저(id)들의 목록
    members : [{ type : String, required : true }]
}, {
    timestamps : true,
    collection : 'rooms'
});

roomSchema.statics.createRoom = async function (name, room_img, uid) {
    try {
        const room = await this.create({ 'name' : name, 'room_img' : room_img, 'members' : [ uid ] });
        return room;
    } catch (err) {
        throw err;
    }
}

roomSchema.statics.deleteRoomById = async function (id) {
    try {
        const room = await this.deleteOne({ _id : id });
        return room;
    } catch (err) {
        throw err;
    }
}

roomSchema.statics.getRoomsByUid = async function (uid) {
    try {
        const rooms = await this.find({ members : { $all : [uid] } });
        console.log(rooms);
        return rooms;
    } catch (err) {
        throw err;
    }
}

roomSchema.statics.getRoomById = async function (id) {
    try {
        const room = await this.findOne({ _id : id });
        return room;
    } catch (err) {
        throw err;
    }
} 

roomSchema.statics.getRooms = async function () {
    try {
        const rooms = await this.find();
        console.log(rooms);
        return rooms;
    } catch (err) {
        throw err;
    }
}

const room_model = mongoose.model('room', roomSchema);

export default room_model;