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
import { changePhoneVerify } from 'api/user'
  

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
  handleClose: () => void
  type: 'change-phone'
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

export default function OTP({ open, handleClose, type, handleAffter }: PropsOTP) {
  const navigate = useNavigate()
  const [code, setCode] = React.useState('')
  const [disable, setDisable] = React.useState(false)

  const handleCode = async () => {

    setDisable(true)
    if (type === 'change-phone') {
      const res = await toast.promise(changePhoneVerify(code), {
        pending: 'Waiting for active account...',
      })
      if (res.status === 204 && type ==='change-phone') {
        handleClose()
        toast.success('Change phone successfully')
        navigate(CONFIG.PAGE_URLS.HOME)
      }else {
        toast.error(res?.data?.message)
      }
    }
    setDisable(false)

  }

  const handleResend = async () => {
    if (type === 'change-phone') {
    //   await updateEmail(email).then(() => {
    //     toast.success('Resend code successfully!')
    //   })
    }
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
          Enter OTP code
        </BootstrapDialogTitle>
        <DialogContent dividers>
          <Typography gutterBottom>
            Please check your email for the confirmation code. The confirmation code is 6 characters long.
          </Typography>
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
              <Typography>We already sent a confirmation code</Typography>
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button variant="outlined" color="secondary" autoFocus onClick={handleResend}>
            Resend the code
          </Button>
          <Button autoFocus onClick={handleCode} variant="contained" color="primary" disabled={disable}>
            Send
          </Button>
        </DialogActions>
      </BootstrapDialog>
    </div>
  )
}
