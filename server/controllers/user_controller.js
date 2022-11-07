import user_model from '../models/user_model.js';
import crypto from 'crypto';
import dotenv from 'dotenv';
import jsonwebtoken from 'jsonwebtoken';
dotenv.config();

export const login = async (req, res) => {
    var id = req.body.id;
    var password = req.body.password;

    var user = await authUser(id, password);

    if (user) {
        try {
            const token = await new Promise((resolve, reject) => {
                jsonwebtoken.sign(
                    { id }, 
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
            res.status(200).json({ 'token' : token, 'message' : '로그인에 성공했습니다.' });
        } catch(err) {
            res.status(401).json({ 'token' : '', 'message' : err });
        }
    } else {
        res.status(401).json({ 'token' : '', 'message' : '로그인에 실패했습니다.' });
    }
};

const authUser = async (id, password) => {
    var user = await user_model.findOne({ id : id }).exec();
    
    if (user) {
        var authenticated = await authenticate(user, password);
        if (authenticated) {
            console.log('사용자 인증 성공 id : ' +  id);
            return true;
        } else {
            console.log('사용자 인증 실패 ')
            return false;
        }
    } else {
        console.log('해당 아이디를 쓰는 사용자가 없습니다.');
        return false;
    }
};

export const signup = async (req, res) => {
    var id = req.body.id;
    var password = req.body.password;
    var name = req.body.name;
    var profile_img = req.file.path;

    var salt = await createSalt();
    var hashed_password = await createHashedPassword(password, salt);

    // 아이디 중복 체크
    var dupId = await user_model.findOne({ id : id }).exec();
    if (dupId) {
        console.log('아이디가 중복입니다.');
        res.status(409).json({ message : '아이디가 중복입니다. 다른 아이디를 설정해주세요.' });
    // 중복된 아이디가 아닐 시 회원가입 실행
    } else {
        var user = user_model({'id' : id, 'hashed_password' : hashed_password, 'name' : name, 'salt' : salt, 'profile_img' : profile_img });
        await user.save();
        
        if (user) {
            console.log('사용자 추가 성공');
            res.status(201).json({ message : '사용자 추가에 성공했습니다.' });
        } else {
            console.log('사용자 추가 실패');
            res.status(400).json({ message : '사용자 추가에 실패했습니다.' });
        }
    }
};

// 비밀번호 암호화에 필요한 salt
const createSalt = async () => {
    return await new Promise((resolve, reject) => {
        crypto.randomBytes(64, (err, buf) => {
            if (err) reject(err);
            resolve(buf.toString('base64'));
        });
    });
};

// 비밀번호 복호화
const createHashedPassword = async (plainPassword, salt) => {
    return await new Promise((resolve, reject) => {
        crypto.pbkdf2(plainPassword, salt, 4096, 64, 'sha512', (err, key) => {
            if (err) reject(err);
            resolve(key.toString('base64'));
        });
    });
};

// 비밀번호 비교를 통해 같은 유저인지 검증
const authenticate = async (user, plainPassword) => {
    var hashed_password = await createHashedPassword(plainPassword, user.salt);

    if (hashed_password == user.hashed_password) {
        return true;
    } else {
        return false;
    }
};