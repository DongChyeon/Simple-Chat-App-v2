import mongoose from 'mongoose';

var userSchema = new mongoose.Schema({
    id : { type : String, required : true, unique : true },
    hashed_password : { type : String, required : true },
    name : { type : String, required : true },
    profile_img : { type : String, required : true },   // 이미지가 아닌 서버상의 이미지 경로를 저장
    salt : { type : String, required : true }
});

const user_model = mongoose.model('user', userSchema);

export default user_model;

/*
몽고DB에 이미지 경로가 아닌 이미지를 저장할려면

user_model.js에서 Schema는 
profile_img : { data : Buffer, ContentType : String }
으로 변경

user_controller.js에서 signup은
var profile_img = {
    data : fs.readFileSync(path.join(__dirname + '/uploads/images/profile/' + req.file.filename)),
    contentType : req.file.mimetype,
};
으로 변경
*/