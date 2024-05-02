import React from 'react'

import { Box, Grid, IconButton, TextField, Typography } from '@mui/material'
import { Controller } from 'react-hook-form'
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft'

import { ButtonAuth } from 'styles/Button'

import styles from 'pages/auth/Auth.module.scss'
import useChangePhone from 'pages/profile/ChangePhone/useChangePhone'
import { CONFIG } from 'common/config'
import OTP from 'components/models/OTP'
export default function ChangePhone(){
    const {user,control, disabled, handleChangePhone, navigate, handleSubmit, handleClose, openModal, setOpenModal} = useChangePhone();
    return (
        <Box
      className={styles.layoutAuth}
      sx={{
        // backgroundImage: `url(${CONFIG.IMAGES.BACKGROUND_AUTH})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundRepeat: 'no-repeat',
      }}
    >
      <Box>
        <Box className={styles.form}>
          <form onSubmit={handleSubmit(handleChangePhone)}>
            <Grid className={styles.boxGrid} container spacing={2}>
              <Grid item xs={12} sx={{ pt: '0px !important' }}>
                <Typography variant="h6">Change Phone</Typography>
              </Grid>
              <Grid item xs={12}>
                <Controller
                  name="phoneNumber"
                  defaultValue={user?.phoneNumber}
                  control={control}
                  render={({ field: { onChange, value } }) => (
                    <TextField
                      placeholder="New phone number"
                      required
                      fullWidth
                      type="text"
                      onChange={onChange}
                      value={value}
                      size="small"
                    />
                  )}
                />
              </Grid>
              <Grid item xs={12}>
                <Controller
                  name="password"
                  control={control}
                  render={({ field: { onChange, value } }) => (
                    <TextField
                      placeholder="Passsword"
                      required
                      fullWidth
                      type="password"
                      onChange={onChange}
                      value={value}
                      size="small"
                    />
                  )}
                />
              </Grid>


              <Grid item xs={12}>
                <ButtonAuth fullWidth type="submit" disabled={disabled} variant="contained">
                  Change Phone
                </ButtonAuth>
              </Grid>
            </Grid>
          </form>
        </Box>
      </Box>

      <IconButton className={styles.btnBack} onClick={() => navigate(CONFIG.PAGE_URLS.HOME)}>
        <ChevronLeftIcon className={styles.iconBack} />
      </IconButton>

      <OTP
        open={openModal}
        handleClose={handleClose}
        type="change-phone"
      />
      
    </Box>
    )
}