import mongoose from 'mongoose';

var userSchema = new mongoose.Schema({
    id : { type : String, required : true, unique : true },
    hashed_password : { type : String, required : true },
    name : { type : String, required : true },
    profile_Img : { type : String, default : '' },
    salt : { type : String, required : true }
});

const user_model = mongoose.model('user', userSchema);

export default user_model;