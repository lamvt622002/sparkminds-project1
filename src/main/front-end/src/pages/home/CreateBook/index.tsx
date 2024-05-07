import MainLayout from "components/layouts/MainLayout";

import { Box, Button, IconButton, TextField, Typography } from '@mui/material'
import ArrowBackIcon from '@mui/icons-material/ArrowBack'
import AddIcon from '@mui/icons-material/Add'
import { Image, Select, Upload } from 'antd'
import CloseIcon from '@mui/icons-material/Close'
import PublishIcon from '@mui/icons-material/Publish'

import styles from 'pages/home/Home.module.scss'
import useCreateBook from "./useCreateBook";
export default function CreateBook() {
    const { categories, languages, publishers, authors, dataBook, disable, setDataBook, beforeUpload, handleCreateBook } = useCreateBook()
    return (
        <MainLayout>
            <Box className="flex_sbw">
                <Box className="flex_row">
                    <IconButton >
                        <ArrowBackIcon sx={{ color: 'white' }} />
                    </IconButton>
                    <Typography variant="h6">Create book</Typography>
                </Box>
                <Box sx={{display:'flex', gap:2}}>
                    <Button variant="contained" startIcon={<AddIcon />} disabled={disable}>
                        <Upload showUploadList={false} beforeUpload={beforeUpload}>
                            Import csv
                            <IconButton>
                                <PublishIcon sx={{ color: 'white' }} />
                            </IconButton>
                        </Upload>
                    </Button>
                    <Button variant="contained" startIcon={<AddIcon />} disabled={disable} onClick={handleCreateBook}>
                        Create book
                    </Button>
                </Box>
                
            </Box>
            <Box className={styles.boxInfo}>
                <Box className={styles.boxField}>
                    <Typography className={styles.boxField__label}>Title</Typography>
                    <TextField
                        className={styles.boxField__input}
                        value={dataBook?.title}
                        onChange={(e) =>
                            setDataBook({
                                ...dataBook,
                                title: e.target.value,
                            })
                        }
                    />
                </Box>
                <Box className={styles.boxField}>
                    <Typography className={styles.boxField__label}>Language</Typography>
                    <Select
                        className={styles.boxField__select}
                        options={languages?.map((language) => ({ label: language.languageName, value: language.id }))}
                        placeholder="Select language"
                        value={dataBook.language}
                        onChange={(value) => {
                            setDataBook({
                                ...dataBook,
                                language: value
                            })
                        }}
                    />
                </Box>
                <Box className={styles.boxField}>
                    <Typography className={styles.boxField__label}>Category</Typography>
                    <Select
                        className={styles.boxField__select}
                        options={categories?.map((category) => ({ label: category.categoryName, value: category.id }))}
                        placeholder="Select category"
                        value={dataBook.category}
                        onChange={(value) => {
                            setDataBook({
                                ...dataBook,
                                category: value
                            })
                        }}
                    />
                </Box>
                <Box className={styles.boxField}>
                    <Typography className={styles.boxField__label}>Publisher</Typography>
                    <Select
                        className={styles.boxField__select}
                        options={publishers?.map((publisher) => ({ label: publisher.publisherName, value: publisher.id }))}
                        placeholder="Select publisher"
                        value={dataBook.publisher}
                        onChange={(value) => {
                            setDataBook({
                                ...dataBook,
                                publisher: value
                            })
                        }
                        }
                    />
                </Box>
                <Box className={styles.boxField}>
                    <Typography className={styles.boxField__label}>Author</Typography>
                    <Select
                        className={styles.boxField__select}
                        options={authors?.map((author) => ({ label: author.fullName, value: author.id }))}
                        placeholder="Select author"
                        value={dataBook.authors}
                        onChange={(value) => {
                            setDataBook({
                                ...dataBook,
                                authors: value
                            })
                        }}

                    />
                </Box>
                <Box className={styles.boxField}>
                    <Typography className={styles.boxField__label}>Price</Typography>
                    <TextField
                        className={styles.boxField__input}
                        type="number"
                        value={dataBook.price}
                        inputProps={{ min: 1, max: 999999 }}
                        onChange={(e) => {
                            setDataBook({
                                ...dataBook,
                                price: parseInt(e.target.value, 10),
                            })
                        }}
                    />
                </Box>
                <Box className={styles.boxField}>
                    <Typography className={styles.boxField__label}>Num of page</Typography>
                    <TextField
                        className={styles.boxField__input}
                        type="number"
                        value={dataBook.numOfPages}
                        inputProps={{ min: 1, max: 900 }}
                        onChange={(e) => {
                            setDataBook({
                                ...dataBook,
                                numOfPages: parseInt(e.target.value, 10),
                            })
                        }}
                    />
                </Box>
                <Box className={styles.boxField}>
                    <Typography className={styles.boxField__label}>Description</Typography>
                    <TextField
                        className={styles.boxField__input}
                        value={dataBook?.description}
                        onChange={(e) =>
                            setDataBook({
                                ...dataBook,
                                description: e.target.value,
                            })
                        }
                    />
                </Box>
                <Box className={styles.boxField}>
                    <Typography className={styles.boxField__label}>Quantity</Typography>
                    <TextField
                        className={styles.boxField__input}
                        type="number"
                        inputProps={{ min: 1, max: 900 }}
                        value={dataBook.quantity}
                        onChange={(e) => {
                            setDataBook({
                                ...dataBook,
                                quantity: parseInt(e.target.value, 10),
                            })
                        }}
                    />
                </Box>

                <Box className={styles.boxField}>
                    <Typography className={styles.boxField__label}>Image</Typography>
                    <Box className={styles.boxField__editImage}>
                        {dataBook?.image && (
                            <IconButton className={styles.boxField__button_close}>
                                <CloseIcon sx={{ fontSize: '16px' }} />
                            </IconButton>
                        )}
                        {dataBook?.image ? (
                            <Image className={styles.img} src={dataBook?.image} alt="upload image" />
                        ) : (
                            <Upload showUploadList={false} beforeUpload={beforeUpload}>
                                <IconButton>
                                    <PublishIcon sx={{ color: 'white' }} />
                                </IconButton>
                            </Upload>
                        )}
                    </Box>
                </Box>
            </Box>
        </MainLayout>
    )
}