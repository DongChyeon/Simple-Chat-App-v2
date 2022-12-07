import user_model from '../models/user_model.js';
import crypto from 'crypto';
import dotenv from 'dotenv';
import jsonwebtoken from 'jsonwebtoken';
dotenv.config();

export const login = async (req, res) => {
    try {
        const id = req.body.id;
        const password = req.body.password;

        const user = await authUser(id, password);
        if (user) {
            const token = await createToken(id);
            console.log(`사용자 로그인 성공 ${id}`);
            return res.status(200).json({ 'token' : token, 'message' : '로그인에 성공했습니다.' });
        } else {
            console.log('사용자 로그인 실패');
            return res.status(401).json({ 'token' : '', 'message' : '로그인에 실패했습니다.' });
        }
    } catch (err) {
        console.log(err);
        return res.status(500).json({ 'token' : '', 'message' : err });
    } 
}

const authUser = async (id, password) => {
    try {
        const user = await user_model.getUserById(id);
        if (user) {
            const authenticated = await authenticate(user, password);
            if (authenticated) {
                console.log(`사용자 인증 성공 ${user.uid}`);
                return true;
            } else {
                console.log('사용자 인증 실패 ')
                return false;
            }
        } else {
            console.log('해당 아이디를 쓰는 사용자가 없습니다.');
            return false;
        }
    } catch (err) {
        console.log(err);
        return false;
    }
}

const createToken = async (id) => {
    return await new Promise((resolve, reject) => {
        jsonwebtoken.sign(
            { uid : id }, 
            process.env.ACCESS_TOKEN_SECRET, 
            { expiresIn : '1d' },
            (err, token) => {
            if (err) {
                reject(err);
            } else {
                resolve(token);
            }
        });
    });
}

const createSalt = async () => {
    return await new Promise((resolve, reject) => {
        crypto.randomBytes(64, (err, buf) => {
            if (err) reject(err);
            resolve(buf.toString('base64'));
        });
    });
}

const encryptPassword = async (plainPassword, salt) => {
    return await new Promise((resolve, reject) => {
        crypto.pbkdf2(plainPassword, salt, 4096, 64, 'sha512', (err, key) => {
            if (err) reject(err);
            resolve(key.toString('base64'));
        });
    });
}

const authenticate = async (user, plainPassword) => {
    const hashed_password = await encryptPassword(plainPassword, user.salt);

    if (hashed_password == user.hashed_password) {
        return true;
    } else {
        return false;
    }
}

export const signup = async (req, res) => {
    try {
        const id = req.body.id;
        const password = req.body.password;
        const name = req.body.name;
        const intro_msg = req.body.intro_msg;
        const profile_img = req.file.path;

        const salt = await createSalt();
        const hashed_password = await encryptPassword(password, salt);

        const dupUser = await user_model.getUserById(id);
        if (dupUser) {
            console.log(`사용자 id ${dupUser.uid} 가 중복입니다.`);
            return res.status(409).json({ 'message' : '아이디가 중복입니다. 다른 아이디를 설정해주세요.' });
        } else {
            const user = await user_model.createUser(id, hashed_password, name, intro_msg, profile_img, salt);
            console.log(`사용자 ${user.uid} 추가 성공`);
            return res.status(201).json({ 'message' : '사용자 추가에 성공했습니다.' });
        }
    } catch (err) {
        console.log(err);
        return res.status(500).json({ 'message' : err });
    }
}

export const getAllUsers = async (req, res) => {
    try {
        const users = await user_model.getAllUsers();
        console.log('유저 목록 불러오기 성공');
        const obj = JSON.parse(JSON.stringify(users));
        return res.status(200).json({ 'users' : obj, 'message' : '유저 목록을 성공적으로 불러왔습니다.' });
    } catch (err) {
        console.log(err);
        return res.status(500).json({ 'users' : [], 'message' : err });
    }
}

export const getUserById = async (req, res) => {
    try {
        const uid = req.params.uid;
        var user = await user_model.getUserById(uid);
        user.profile_img = process.env.IP_ADDRESS + user.profile_img;
        console.log('유저 정보 불러오기 성공');
        const obj = JSON.parse(JSON.stringify([user]));
        return res.status(200).json({ 'users' : obj, 'message' : '유저 프로필을 성공적으로 불러왔습니다.'});
    } catch (err) {
        console.log(err);
        return res.status(500).json({ 'users' : [], 'message' : err });
    }
}

// 본인을 제외한 접속 중인 유저를 보여줌
export const getOnlineUsers = async (req, res) => {
    try {
        var users = await user_model.getOnlineUsers();
        users = users.filter(user => user.uid != req.tokenInfo.uid);
        for (let i = 0; i < users.length; i++) {
            users[i].profile_img = process.env.IP_ADDRESS + users[i].profile_img;
        }
        console.log('유저 목록 불러오기 성공');
        const obj = JSON.parse(JSON.stringify(users));
        return res.status(200).json({ 'users' : obj, 'message' : '유저 목록을 성공적으로 불러왔습니다.' });
    } catch (err) {
        console.log(err);
        return res.status(500).json({ 'users' : [], 'message' : err });
    }
}

export const updateProfile = async (req, res) => {
    try {
        const id = req.body.id;
        const name = req.body.name;
        const intro_msg = req.body.intro_msg;
        
        var user = await user_model.updateProfile(id, name, intro_msg);
        user.profile_img = process.env.IP_ADDRESS + user.profile_img;
        console.log('프로필 수정 성공');
        const obj = JSON.parse(JSON.stringify([user]));
        return res.status(200).json({ 'users' : obj, 'message' : '프로필 수정을 성공했습니다.' });
    } catch (err) {
        console.log(err);
        return res.status(500).json({ 'users' : [], 'message' : err });
    }
}

export const updateProfileWithImg = async (req, res) => {
    try {
        const id = req.body.id;
        const name = req.body.name;
        const intro_msg = req.body.intro_msg;
        const profile_img = req.file.path;
        
        var user = await user_model.updateProfileWithImg(id, name, intro_msg, profile_img);
        user.profile_img = process.env.IP_ADDRESS + user.profile_img;
        console.log('프로필 수정 성공');
        const obj = JSON.parse(JSON.stringify([user]));
        return res.status(200).json({ 'users' : obj, 'message' : '프로필 수정을 성공했습니다.' });
    } catch (err) {
        console.log(err);
        return res.status(500).json({ 'users' : [], 'message' : err });
    }
}

// 소켓 연결 상태가 바뀔 때마다 호출
export const updateUserStatus = async (uid, status) => {
    try {
        const user = await user_model.updateUserStatus(uid, status);
        console.log(`유저 ${user.uid} 상태 업데이트 성공 : ${user.status}`);
    } catch (err) {
        console.log(err);
    }
}