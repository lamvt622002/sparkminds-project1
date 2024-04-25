import React from "react";
import { SubmitHandler, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import userStore from "stores/user";
import { toast } from "react-toastify";
import { changeEmail } from "api/user";
import { CONFIG } from "common/config";
import { ERROR_MESSAGE, validatePass } from "utils/regexValidate";
import { changePassword } from "api/user";

type FormData ={
    oldPassword:string,
    newPassword:string,
    confirmNewPassword:string
}

const useChangePassword = () => {
    const { user, setDataUser } = userStore()
    const navigate = useNavigate()
    const { handleSubmit, control } = useForm<FormData>()
    const [disabled, setDisabled] = React.useState(false)

    const handleChangePassword: SubmitHandler<FormData> = async (data) => {
      if(!validatePass(data.oldPassword) || !validatePass(data.newPassword)|| !validatePass(data.confirmNewPassword)){
        toast.error(ERROR_MESSAGE.password)
        return
       }
        if (data.newPassword !== data.confirmNewPassword) {
          toast.error('New password and confirm password must be the same')
          return
        }
        setDisabled(true)
    
        const res = await toast.promise(changePassword(data.oldPassword, data.newPassword, data.confirmNewPassword), {
          pending: 'Waiting for request change password...',
        })
        if (res.status === 204) {
          toast.success("Change password successfully")
          navigate(CONFIG.PAGE_URLS.VERIFY_EMAIL)
        } else {
          toast.error(res.data.message)
        }
        setDisabled(false)
    } 
    return{user,control, disabled, handleChangePassword, navigate, handleSubmit} 
}
export default useChangePassword;