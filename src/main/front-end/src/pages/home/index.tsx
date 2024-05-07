import { Box, Button, Grid, TextField, Typography } from '@mui/material'
import { Select } from 'antd'
import AutorenewIcon from '@mui/icons-material/Autorenew'
import RotateLeftIcon from '@mui/icons-material/RotateLeft'
import styles from "./Home.module.scss"

import Table from "components/Table";
import useHome from "./useHome";
import MainLayout from "components/layouts/MainLayout";


export default function HomePage(){
    const {columns, books, loading, query, setQuery, categories, languages, publishers,handleOnCellClick, handleChangePage, totalPage, currentPage} = useHome()
    return(
        <MainLayout>
            <Box
        sx={{
          my: 2,
          p: 2,
          border: '1px solid #e0e0e0',
          borderRadius: '5px',
        }}
      >
        <Box className="flex_sbw" mb={1}>
          <Typography variant="h6">Filter</Typography>
          <Box>
            <Button
              className={styles.btnSwitch}
              startIcon={<RotateLeftIcon />}
              sx={{ mr: 1 }}
            >
              Reset filter
            </Button>
            <Button
              className={styles.btnSwitch}
              startIcon={<AutorenewIcon />}
            >
              Switch to 
            </Button>
          </Box>
        </Box>
        <Grid container spacing={2}>
          <Grid item xs={4}>
            <Select
              mode="tags"
              className={styles.select}
              style={{ width: '100%' }}
              tokenSeparators={[',']}
              placeholder="Filter by category"
              onChange={(e) => setQuery((prev) => ({ ...prev, category: e }))}
              value={query.category}
              options={categories.map((item) => ({label:item.categoryName,  value:item.id}))}
            />
          </Grid>
          <Grid item xs={4}>
            <Select
              mode="tags"
              className={styles.select}
              style={{ width: '100%' }}
              tokenSeparators={[',']}
              placeholder="Filter by language"
              onChange={(e) => setQuery((prev) => ({ ...prev, language: e }))}
              value={query.language}
              options={languages.map((item) => ({label:item.languageName,  value:item.id}))}
            />
          </Grid>
          <Grid item xs={4}>
            <Select
              mode="tags"
              className={styles.select}
              style={{ width: '100%' }}
              tokenSeparators={[',']}
              placeholder="Filter by publisher"
              onChange={(e) => setQuery((prev) => ({ ...prev, publisher: e }))}
              value={query.publisher}
              options={publishers.map((item) => ({label:item.publisherName,  value:item.id}))}
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              className={styles.searchInput}
              placeholder="Search..."
              value={query?.search}
              onChange={(e) => setQuery((prev) => ({ ...prev, search: e.target.value }))}
            />
          </Grid>
          <Grid item xs={4}>
            <Typography mr={1} whiteSpace="nowrap" fontSize={14}>
              Sort by
            </Typography>
            <Select
              className={styles.select}
              defaultValue=""
              value={query.sortByPrice}
              onChange={(value) => setQuery((prev) => ({ ...prev, sortByPrice: value as string }))}
              options={[
                { value: '', label: 'Default' },
                { value: 'price', label: 'Price' },
                { value: 'quantity', label: 'Quantity' },
                { value: 'numOfPage', label: 'NumOfPage' },
              ]}
            />
          </Grid>
          <Grid item xs={4}>
            <Typography mr={1} whiteSpace="nowrap" fontSize={14}>
              Price min
            </Typography>
            <TextField
              type="number"
              className={styles.searchInput}
              value={query?.min_price}
              onChange={(e) => setQuery((prev) => ({ ...prev, min_price: +e.target.value }))}
            />
          </Grid>
          <Grid item xs={4}>
            <Typography mr={1} whiteSpace="nowrap" fontSize={14}>
              Price max
            </Typography>
            <TextField
              type="number"
              className={styles.searchInput}
              value={query?.max_price}
              onChange={(e) => setQuery((prev) => ({ ...prev, max_price: +e.target.value }))}
            />
          </Grid>
        </Grid>
      </Box>
            <Table columns={columns} rows={books} loading={loading} handleOnCellClick={handleOnCellClick} handleChangePage={handleChangePage} totalPage={totalPage} currentPage={currentPage}/>
        </MainLayout>
       
    )
}