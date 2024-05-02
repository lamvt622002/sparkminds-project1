import React from 'react'

import { Box, Grid, IconButton, TextField, Typography } from '@mui/material'
import { Controller } from 'react-hook-form'
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft'

import { ButtonAuth } from 'styles/Button'

import styles from 'pages/auth/Auth.module.scss'
import useChangePassword from 'pages/profile/ChangePassword/useChangePassword'
import { CONFIG } from 'common/config'
export default function ChangePassword(){
    const {control, disabled, handleChangePassword, navigate, handleSubmit} = useChangePassword();
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
          <form onSubmit={handleSubmit(handleChangePassword)}>
            <Grid className={styles.boxGrid} container spacing={2}>
              <Grid item xs={12} sx={{ pt: '0px !important' }}>
                <Typography variant="h6">Change Password</Typography>
              </Grid>
              <Grid item xs={12}>
                <Controller
                  name="oldPassword"
                  control={control}
                  render={({ field: { onChange, value } }) => (
                    <TextField
                      placeholder="Old password"
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
                <Controller
                  name="newPassword"
                  control={control}
                  render={({ field: { onChange, value } }) => (
                    <TextField
                      placeholder="New passsword"
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
                <Controller
                  name="confirmNewPassword"
                  control={control}
                  render={({ field: { onChange, value } }) => (
                    <TextField
                      placeholder="Confirm new passsword"
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
                  Change Password
                </ButtonAuth>
              </Grid>
            </Grid>
          </form>
        </Box>
      </Box>

      <IconButton className={styles.btnBack} onClick={() => navigate(CONFIG.PAGE_URLS.HOME)}>
        <ChevronLeftIcon className={styles.iconBack} />
      </IconButton>
    </Box>
    )
}