import MainLayout from "components/layouts/MainLayout";

import { Box, Button, IconButton, TextField, Typography } from '@mui/material'
import ArrowBackIcon from '@mui/icons-material/ArrowBack'
import AddIcon from '@mui/icons-material/Add'
import { Image, Select, Upload } from 'antd'
import CloseIcon from '@mui/icons-material/Close'
import PublishIcon from '@mui/icons-material/Publish'

import styles from 'pages/home/Home.module.scss'
import useEditBook from "./useEditBook";
export default function EditBook() {
    const { categories, languages, publishers, authors, disable, book, setBook, beforeUpload, handleEditBook, removeImage } = useEditBook()
    return (
        <MainLayout>
            <Box className="flex_sbw">
                <Box className="flex_row">
                    <IconButton >
                        <ArrowBackIcon sx={{ color: 'white' }} />
                    </IconButton>
                    <Typography variant="h6">Edit book</Typography>
                </Box>
                <Button variant="contained" startIcon={<AddIcon />} disabled={disable} onClick={handleEditBook}>
                    Edit book
                </Button>
            </Box>
            <Box className={styles.boxInfo}>
                <Box className={styles.boxField}>
                    <Typography className={styles.boxField__label}>Title</Typography>
                    <TextField
                        className={styles.boxField__input}
                        defaultValue={book?.title}
                        value={book?.title}
                        onChange={(e) =>
                            setBook({
                                ...book,
                                title: e.target.value,
                            })
                        }
                    />
                </Box>
                <Box className={styles.boxField}>
                    <Typography className={styles.boxField__label}>Language</Typography>
                    <Select
                        className={styles.boxField__select}
                        options={languages?.map((language) => ({ label: language.languageName, value: language.languageName }))}
                        placeholder="Select language"
                        value={book.language}
                        onChange={(value) => {
                            setBook({
                                ...book,
                                language: value
                            })
                        }}
                    />
                </Box>
                <Box className={styles.boxField}>
                    <Typography className={styles.boxField__label}>Category</Typography>
                    <Select
                        className={styles.boxField__select}
                        options={categories?.map((category) => ({ label: category.categoryName, value: category.categoryName }))}
                        placeholder="Select category"
                        value={book.category}
                        onChange={(value) => {
                            setBook({
                                ...book,
                                category: value
                            })
                        }}
                    />
                </Box>
                <Box className={styles.boxField}>
                    <Typography className={styles.boxField__label}>Publisher</Typography>
                    <Select
                        className={styles.boxField__select}
                        options={publishers?.map((publisher) => ({ label: publisher.publisherName, value: publisher.publisherName }))}
                        placeholder="Select publisher"
                        value={book.publisher}
                        onChange={(value) => {
                            setBook({
                                ...book,
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
                        options={authors?.map((author) => ({ label: author.fullName, value: author.fullName }))}
                        placeholder="Select author"
                        value={book.authorName}
                        onChange={(value) => {
                            const newAuthorName = typeof value === "string" ? [value] : value;
                            setBook({
                                ...book,
                                authorName: newAuthorName
                            })
                        }}

                    />
                </Box>
                <Box className={styles.boxField}>
                    <Typography className={styles.boxField__label}>Price</Typography>
                    <TextField
                        className={styles.boxField__input}
                        type="number"
                        value={book.price}
                        inputProps={{ min: 1, max: 999999 }}
                        onChange={(e) => {
                            setBook({
                                ...book,
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
                        value={book.numOfPages}
                        inputProps={{ min: 1, max: 900 }}
                        defaultValue={book.numOfPages}
                        onChange={(e) => {
                            setBook({
                                ...book,
                                numOfPages: parseInt(e.target.value, 10),
                            })
                        }}
                    />
                </Box>
                <Box className={styles.boxField}>
                    <Typography className={styles.boxField__label}>Description</Typography>
                    <TextField
                        className={styles.boxField__input}
                        value={book?.description}
                        onChange={(e) =>
                            setBook({
                                ...book,
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
                        value={book.quantity}
                        onChange={(e) => {
                            setBook({
                                ...book,
                                quantity: parseInt(e.target.value, 10),
                            })
                        }}
                    />
                </Box>

                <Box className={styles.boxField}>
                    <Typography className={styles.boxField__label}>Image</Typography>
                    <Box className={styles.boxField__editImage}>
                        {book?.image && (
                            <IconButton className={styles.boxField__button_close} onClick={removeImage}>
                                <CloseIcon sx={{ fontSize: '16px' }} />
                            </IconButton>
                        )}
                        {book?.image ? (
                            <Image className={styles.img} src={book?.image} alt="upload image" />
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