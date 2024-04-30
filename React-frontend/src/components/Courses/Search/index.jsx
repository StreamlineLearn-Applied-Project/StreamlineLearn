import React, { useState } from 'react';
import SearchRoundedIcon from '@mui/icons-material/SearchRounded';
import "./styles.css";

function Search({search, onSearchChange}) {
  
  return (
    <div className='search-flex'>
        <SearchRoundedIcon style={{color: 'var(--white)'}}/>
        <input 
            placeholder='Search' 
            type='text' 
            value={search}
            onChange={(e)=> onSearchChange(e)}
            style={{color: 'var(--white)'}}
        />
    </div>
  )
}

export default Search;
