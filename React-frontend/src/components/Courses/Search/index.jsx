import React, { useState } from 'react';
import SearchRoundedIcon from '@mui/icons-material/SearchRounded';
import "./styles.css";

function Search({search, onSearchChange}) {
  
  return (
    <div className='search-flex'>
        <SearchRoundedIcon/>
        <input 
            placeholder='Search' 
            type='text' 
            value={search}
            onChange={(e)=> onSearchChange(e)}
        />
    </div>
  )
}

export default Search;
