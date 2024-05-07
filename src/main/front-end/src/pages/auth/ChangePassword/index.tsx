import { ButtonAuth } from "styles/Button";
import useChangePassword from "pages/auth/ChangePassword/useChangePassword";
import { Box, Grid, TextField, Typography } from "@mui/material";
import {Controller} from 'react-hook-form';

import styles from 'pages/auth/Auth.module.scss';



const ChangePassword = () => {
    const {disable, control, handleChangePassword, handleSubmit} = useChangePassword();
    return(
        <Box className={styles.layoutAuth}>
            <Box className={styles.mainLayout}>
                <Typography variant="h3" className={styles.formHeader}>
                    Change password
                </Typography>
                <form onSubmit={handleSubmit(handleChangePassword)}>
                    <Grid container spacing={2}>
                    <Grid item xs={12}>
                            <Controller
                                name="email"
                                defaultValue={""}
                                control={control}
                                render={({field: {onChange, value}}) => (
                                    <TextField
                                        required
                                        name="email"
                                        placeholder="Email"
                                        type="email"
                                        fullWidth
                                        onChange={onChange}
                                        value={value}
                                        size="small"
                                    />
                                )}
                            />

                        </Grid>
                        <Grid item xs={12}>
                            <Controller
                                name="old_password"
                                defaultValue={""}
                                control={control}
                                render={({field: {onChange, value}}) => (
                                    <TextField
                                        required
                                        name="old_password"
                                        placeholder="Password"
                                        type="password"
                                        fullWidth
                                        onChange={onChange}
                                        value={value}
                                        size="small"
                                    />
                                )}
                            />

                        </Grid>
                        <Grid item xs={12}>
                            <Controller
                                name="new_password"
                                defaultValue={""}
                                control={control}
                                render={({field: {onChange, value}}) => (
                                    <TextField
                                        required
                                        name="new_password"
                                        placeholder="New password"
                                        type="password"
                                        fullWidth
                                        onChange={onChange}
                                        value={value}
                                        size="small"
                                    />
                                )}
                            />

                        </Grid>
                        <Grid item xs={12}>
                            <Controller
                                name="comfirm_new_password"
                                defaultValue={""}
                                control={control}
                                render={({field: {onChange, value}}) => (
                                    <TextField
                                        required
                                        name="confirm_new_password"
                                        placeholder="Confirm new password"
                                        type="password"
                                        fullWidth
                                        onChange={onChange}
                                        value={value}
                                        size="small"
                                    />
                                )}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <ButtonAuth fullWidth type="submit" variant="contained" disabled={disable}>
                                Submit
                            </ButtonAuth>
                        </Grid>
                        <Grid item xs={12} className={styles.authLink}>
                            <Typography variant="body2">
                                <a href="/login" className={styles.link}>
                                    Already have an account
                                </a>
                            </Typography>
                            <Typography variant="body2">
                                <a href="/register" className={styles.link}>
                                    Dont have an accoun
                                </a>
                            </Typography>
                        </Grid>
                    </Grid>
                </form>
            </Box>
        </Box>
    )
}

export default ChangePassword