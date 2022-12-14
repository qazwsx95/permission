import{mock} from 'mockjs'



export const updateById=mock(/\/account/,'post',{
    'code': 200,
    'msg': '操作成功'
})

export const deleteByIds=mock(/\/account\??(.*)*/,'delete',{
    'code': 200,
    'msg': '操作成功'
})
export const insertAccount=mock(/\/account\??(.*)*/,'put',{
    'code': 200,
    'msg': '操作成功',
    data: {
        'id|+1':1
    }
})

export const updateStatus=mock(/\/account\/change/,'post',{
        'code': 200,
        'msg': '操作成功'
    })



export const findAll=mock(
    //?出现一次或0次
    /\/account\??(.*)*/,
    'get',
    //改成mock函数
    function ({url}) {
        let params = {}
        if (url.indexOf('?')){
            const split = url = url.split('?')[1].split('&');
            for (let param of split) {
                params[param.split('=')[0]] = param.split('=')[1]
            }
        }
        const {page,pageSize} = params

        return mock({
            'code': 200,
            'msg': 'success',
            'data': {
                [`list|${pageSize}`]:[
                    {
                        'id|+1':1,
                        'username':'@word(6,10)',
                        'updateTime':'@datetime',
                        'createTime':'@datetime',
                        'status|1':[0,1]
                    }
                ],
                pageInfo: {
                    total:105,
                    page:page,
                    pageSize:pageSize
                }
            }
        })
    }


    // {
    //     'code': 200,
    //     'msg': 'success',
    //     'data': {
    //         'list|10':[
    //             {
    //                 'id|+1':1,
    //                 'username':'@word(6,10)',
    //                 'updateTime':'@datetime',
    //                 'createTime':'@datetime',
    //                 'status|1':[0,1]
    //             }
    //         ],
    //         pageInfo: {
    //             total:10,
    //             page:1,
    //             pageSize:10
    //         }
    //     }
    // }
)