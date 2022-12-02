import mongoose from 'mongoose';

const userSchema = new mongoose.Schema({
    uid : { type : String, required : true, unique : true },
    hashed_password : { type : String, required : true },
    name : { type : String, required : true },
    intro_msg : { type : String, required : true },
    profile_img : { type : String, required : true },   // 이미지가 아닌 서버상의 이미지 경로를 저장
    status : { type : String, enum : [ 'ONLINE', 'OFFLINE' ], default : () => 'ONLINE' },
    salt : { type : String, required : true }
}, {
    timestamps : true,
    collection : 'users'
})

userSchema.statics.createUser = async function (id, hashed_password, name, intro_msg, profile_img, salt) {
    try {
        const user = await this.create({ 
            uid : id, 
            hashed_password : hashed_password, 
            name : name,
            intro_msg : intro_msg,
            profile_img : profile_img,
            salt : salt
        });
        return user;
    } catch (err) {
        throw 'createUser 에러 : ' + err;
    }
}

userSchema.statics.getUserById = async function (id) {
    try {
        const user = await this.findOne({ uid : id });
        return user;
    } catch (err) {
        throw 'getUserById 에러 : ' + err;
    }
}

userSchema.statics.getAllUsers = async function () {
    try {
        const users = await this.find({}, {
            _id : 0,
            uid : 1,
            name : 1,
            intro_msg : 1,
            profile_img : 1
        });
        return users;
    } catch (err) {
        throw 'getAllUsers 에러 : ' + err;
    }
}

userSchema.statics.getOnlineUsers = async function () {
    try {
        const users = await this.find({ status : 'ONLINE' }, {
            _id : 0,
            uid : 1,
            name : 1,
            intro_msg : 1,
            profile_img : 1
        });
        return users;
    } catch (err) {
        throw 'getOnlineUsers 에러 : ' + err;
    }
}

userSchema.statics.updateUserStatus = async function (id, status) {
    try {
        const user = await this.findOneAndUpdate(
            { uid : id }, { $set : { status : status }}, { new : true }
        );
        return user;
    } catch (err) {
        throw 'updateUserStatus 에러 : ' + err;
    }
}

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