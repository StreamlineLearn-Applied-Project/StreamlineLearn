import * as React from 'react';
import {Box, createTheme, ThemeProvider} from '@mui/material';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';
import Grid from '../Grid';
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
    color: "var(--white)",
    "& .Mui-selected": {
      color: "var(--blue) !important",
    },
    fontFamily: "Inter,sans-serif",
    fontWeight: 600,
    textTransform: "capitalize",
  };

  return (
    <ThemeProvider theme={Theme}>
      <TabContext value={value}>
          <TabList onChange={handleChange} variant='fullWidth'>
            <Tab label="Grid " value="grid" sx={style} />
            {/* <Tab label="List" value="list" sx={style} /> */}
          </TabList>
        <TabPanel value="grid">
          <div className='grid-flex'>
            {courses.map((course, i) => {
              return (
                 <Grid course={course} key={i}/>
              )
            })}
          </div>
        </TabPanel>
        {/* <TabPanel value="list"> */}
          {/* <div>
            {courses.map((course, i) => {
              return (
                <Grid course={course} key={i}/>
              )
            })}
          </div> */}
        {/* </TabPanel> */}
      </TabContext>
    </ThemeProvider>
  );
}