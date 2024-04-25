import * as React from 'react'
import Button from '@mui/material/Button'
import { styled } from '@mui/material/styles'
import Dialog from '@mui/material/Dialog'
import DialogTitle from '@mui/material/DialogTitle'
import DialogContent from '@mui/material/DialogContent'
import DialogActions from '@mui/material/DialogActions'
import IconButton from '@mui/material/IconButton'
import Typography from '@mui/material/Typography'
import { Grid, TextField } from '@mui/material'
import CloseIcon from '@mui/icons-material/Close'
import { toast } from 'react-toastify'
import { useNavigate } from 'react-router-dom'
import { grey } from '@mui/material/colors'

import { CONFIG } from 'common/config'
import { twoAuth } from 'api/auth'
import useStore from "stores/user"
  

const BootstrapDialog = styled(Dialog)(({ theme }) => ({
  '& .MuiDialogContent-root': {
    padding: theme.spacing(2),
  },
  '& .MuiDialogActions-root': {
    padding: theme.spacing(1),
  },
  '& .MuiDialog-paper': {
    margin: 0,
    backgroundColor: grey[800],
  },
}))

export interface DialogTitleProps {
  id: string
  children?: React.ReactNode
  onClose: () => void
}

interface PropsOTP {
  open: boolean
  qrCode:string
  email:string
  handleClose: () => void
  handleAffter?: () => void
}

function BootstrapDialogTitle(props: DialogTitleProps) {
  const { children, onClose, ...other } = props

  return (
    <DialogTitle sx={{ m: 0, p: 2 }} {...other}>
      {children}
      {onClose ? (
        <IconButton
          aria-label="close"
          onClick={onClose}
          sx={{
            position: 'absolute',
            right: 8,
            top: 8,
            color: (theme) => theme.palette.grey[500],
          }}
        >
          <CloseIcon />
        </IconButton>
      ) : null}
    </DialogTitle>
  )
}

export default function GoogleAuthModel({ open, qrCode, email, handleClose, handleAffter }: PropsOTP) {
  const navigate = useNavigate()
  const [code, setCode] = React.useState('')
  const [disable, setDisable] = React.useState(false)
  const {setDataUser} = useStore();

  const handleCode = async () => {
    setDisable(true)
    const res = await toast.promise(twoAuth(email,code), {
    pending: 'Waiting for login...',
    })

    if (res.status === 200) {
      setDataUser(res?.data?.data)
      handleClose()
      toast.success('login successfully')
      navigate(CONFIG.PAGE_URLS.HOME)
    }else {
     toast.error(res?.data?.message)
    }
      setDisable(false)
  }

  React.useEffect(() => {
    if (open) {
      setCode('')
    }
  }, [open])

  return (
    <div>
      <BootstrapDialog onClose={handleClose} aria-labelledby="customized-dialog-title" open={open}>
        <BootstrapDialogTitle id="customized-dialog-title" onClose={handleClose}>
          Enter TOTP
        </BootstrapDialogTitle>
        <DialogContent dividers>
          <Typography gutterBottom>
            Please use Google Authenticator to scan
          </Typography>
          
            <img src={qrCode} alt="PNG Image" style={{marginBottom:5}}/>
          
          <Grid
            container
            spacing={2}
            sx={{
              alignItems: 'center',
            }}
          >
            <Grid item xs={4}>
              <TextField
                size="small"
                value={code}
                label="Enter code"
                onChange={(e) => {
                  setCode(e.target.value)
                }}
              />
            </Grid>
            <Grid item xs={8}>
              <Typography>Use google authenticator to get the code</Typography>
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button autoFocus onClick={handleCode} variant="contained" color="primary" disabled={disable}>
            Send
          </Button>
        </DialogActions>
      </BootstrapDialog>
    </div>
  )
}
