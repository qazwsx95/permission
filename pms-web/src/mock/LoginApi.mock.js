import {mock} from 'mockjs'

export default mock(/\/login/,'post',{
    "msg": "OK",
    "code":200,
    "data": {
        //8966f16b-2dda-4d1e-ad15-26ca4e506f4a
        "token": /\w{8}-\w{4}-\w{4}-\w{4}-\w{12}/,
        "permission": [
            "account:insert",
            "account:login",
            "account:delete"
        ],
        "roles": [
            "account",
            "role",
            "permission"
        ]
    }
})