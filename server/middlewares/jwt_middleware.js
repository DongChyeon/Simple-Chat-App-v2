import jsonwebtoken from 'jsonwebtoken';
dotenv.config();

export const verifyToken = async (req, res, next) => {
    const token = req.headers['authorization'];

    if (token == null) {
        return res.status(403).json({ message : '사용자 인증 실패' });
    } else {
        try {
            const tokenInfo = await new Promise((resolve, reject) => {
                jsonwebtoken.verify(token, process.env.ACCESS_TOKEN_SECRET,
                    (err, decoded) => {
                        if (err) {
                            reject(err);
                        } else {
                            resolved(decoded);
                        }
                    })
            });
            req.tokenInfo = tokenInfo;
            next();
        } catch (err) {
            return res.status(403).json({ message : '사용자 인증 실패' });
        }
    }
};