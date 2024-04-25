import React from "react"
import useStore from "stores/user"

import { useForm, SubmitHandler } from "react-hook-form"
import { useNavigate } from "react-router-dom"
import { ERROR_MESSAGE, validatePass } from "utils/regexValidate"
import { toast } from "react-toastify"
import { changePassword } from "api/auth"

type FormData ={ 
    email:string,
    old_password:string,
    new_password: string,
    comfirm_new_password:string
}
const useChangePassword = () => {
    const {handleSubmit, control} = useForm<FormData>() 
    const [disable, setDisable] = React.useState(false)
    const navigate = useNavigate();
    const {setDataUser} = useStore();

    const handleChangePassword: SubmitHandler<FormData> = async (data) =>{
        if(!validatePass(data.old_password) || !validatePass(data.new_password)){
            toast.error(ERROR_MESSAGE.password)
            return
        }

        if(data.new_password !== data.comfirm_new_password){
            toast.error("Password and confirm password must be the same")
            return
        }

        setDisable(true)
        const res = await toast.promise(changePassword(data.email, data.old_password, data.new_password), {pending: 'Waiting for change password'})

        if(res.status === 204){
            toast.success("Change password successfully!")
            navigate("/login")
        }
        else
        {
            toast.error(res.data.message)
        }
        setDisable(false)
        
    }
    return {disable, control, handleSubmit, handleChangePassword}
}

export default useChangePassword