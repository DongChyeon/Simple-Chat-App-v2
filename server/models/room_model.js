import mongoose from 'mongoose';

var roomSchema = new mongoose.Schema({
    name : { type : String, required : true },
    room_img : { type : String, required : true },
    // 채팅방에 있는 유저(id)들의 목록
    users : [{ type : String, required : true }]
});

const room_model = mongoose.model('room', roomSchema);

export default room_model;