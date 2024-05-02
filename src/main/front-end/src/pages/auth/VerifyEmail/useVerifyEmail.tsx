import React from "react"

import {useParams } from "react-router-dom"
import { toast } from "react-toastify"
import { resentVerifyLink } from "api/auth"

const useVerifyEmail = () => {
    const [disable, setDisable] = React.useState(false)

    const {email} = useParams();

    const handleResentLink = async () =>{
        if(email){
            setDisable(true)
            const res = await toast.promise(resentVerifyLink(email), {pending: 'Waiting for resent verify email'})
    
            if(res.status === 204){
                toast.success("resent email successfully")
            }
            else
            {
                toast.error(res.data.message)
            }
            setDisable(false)
        }        
    }
    return {disable, email, handleResentLink}
}

export default useVerifyEmail