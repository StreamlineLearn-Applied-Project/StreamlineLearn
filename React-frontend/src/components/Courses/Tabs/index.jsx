import * as React from 'react';
import {Box, createTheme, ThemeProvider} from '@mui/material';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';
import Grid from '../Grid';
import List from '../List';
import "./styles.css";

export default function TabsComponent({courses}) {
  const [value, setValue] = React.useState('grid');

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

const Theme = createTheme({
palette: {
        primary: {
            main: "#ffffff",
        },
    },
});

    

  const style = {
    color: {
      light: "var(--black)", 
      dark: "var(--green)"   
  },
    "& .Mui-selected": {
      color: {
        light: "var(--black)", 
        dark: "var(--green)"  
      },
    },
    fontFamily: "Inter,sans-serif",
    fontWeight: 600,
    textTransform: "capitalize",
  };

  return (
    <ThemeProvider theme={Theme}>
      <TabContext value={value}>
        <div className='tab-container'>
          <TabList onChange={handleChange} variant='fullWidth'>
              <Tab label="Grid " value="grid" sx={style}  style={{outline: "none", textDecoration: "none", MuiSelected: {color: "var(--red)"}}}/>
              <Tab label="List" value="list" sx={style}  style={{outline: "none", textDecoration: "none"}}/>
            </TabList>
        </div>
        <TabPanel value="grid">
          <div className='grid-flex'>
            {courses.map((course, i) => {
              return (
                 <Grid course={course} key={i}/>
              )
            })}
          </div>
        </TabPanel>
        <TabPanel value="list"> 
          <table>
            {courses.map((course, i) => {
              return (
                <List course={course} key={i}/>
              )
            })}
          </table> 
         </TabPanel>
      </TabContext>
    </ThemeProvider>
  );
}