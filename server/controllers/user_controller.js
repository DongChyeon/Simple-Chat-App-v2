import user_model from '../models/user_model.js';
import crypto from 'crypto';

export const login = async (req, res) => {
    var id = req.body.id;
    var password = req.body.password;

    var user = await authUser(id, password);

    if (user) {
        res.status(200).json({ 'token' : 'token' });
    } else {
        res.status(401).json({ 'token' : '' });
    }
};

const authUser = async (id, password) => {
    var user = await user_model.findOne({ id : id }).exec();;
    
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

    var salt = await createSalt();
    var hashed_password = await createHashedPassword(password, salt);

    var user = user_model({'id' : id, 'hashed_password' : hashed_password, 'name' : name, 'salt' : salt });
    await user.save();
    
    if (user) {
        console.log('사용자 추가 성공');
        res.status(201).json({ result : 1 });
    } else {
        console.log('사용자 추가 실패');
        res.status(400).json({ result : 0 });
    }
};

const createSalt = async () => {
    return await new Promise((resolve, reject) => {
        crypto.randomBytes(64, (err, buf) => {
            if (err) reject(err);
            resolve(buf.toString('base64'));
        });
    });
};

const createHashedPassword = async (plainPassword, salt) => {
    return await new Promise((resolve, reject) => {
        crypto.pbkdf2(plainPassword, salt, 4096, 64, 'sha512', (err, key) => {
            if (err) reject(err);
            resolve(key.toString('base64'));
        });
    });
};

const authenticate = async (user, plainPassword) => {
    var hashed_password = await createHashedPassword(plainPassword, user.salt);

    if (hashed_password == user.hashed_password) return true;
    else return false;
};