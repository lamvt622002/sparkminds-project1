import { ButtonAuth } from "styles/Button";
import useForgotPassword from "pages/auth/ForgotPassword/useForgotPassword";
import { Box, Grid, TextField, Typography } from "@mui/material";
import {Controller} from 'react-hook-form';

import styles from 'pages/auth/Auth.module.scss';



const ForgotPassword = () => {
    const {disable, control, handleForgotPassword, handleSubmit} = useForgotPassword();
    return(
        <Box className={styles.layoutAuth}>
            <Box className={styles.mainLayout}>
                <Typography variant="h3" className={styles.formHeader}>
                    Forgot password
                </Typography>
                <form onSubmit={handleSubmit(handleForgotPassword)}>
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
                                    Dont have an account
                                </a>
                            </Typography>
                        </Grid>
                    </Grid>
                </form>
            </Box>
        </Box>
    )
}

export default ForgotPassword