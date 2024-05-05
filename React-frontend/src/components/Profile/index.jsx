import React from 'react';
import './styles.css';

function ProfileDetails({ user }) {
    return (
        <div className="profile-details">
            <h1 style={{ textAlign: 'left', fontWeight: 'bold', position: 'absolute', paddingRight: '1%', marginLeft: '42%' }}>Profile Details</h1>
            <div className='profile-details-info'>
                <p><strong>ID:</strong></p>
                <p>{user.id}</p>
                <p><strong>First Name:</strong></p>
                <p>{user.firstName}</p>
                <p><strong>Last Name:</strong></p>
                <p>{user.lastName}</p>
                <p><strong>Username:</strong></p>
                <p>{user.username}</p>
                <p><strong>Role:</strong></p>
                <p>{user.role}</p>
                <p><strong>Enabled:</strong></p>
                <p>{user.enabled ? 'Yes' : 'No'}</p>
                <p><strong>Authorities:</strong></p>
                <p>{user.authorities.map(auth => auth.authority).join(', ')}</p>
                <p><strong>Credentials Non-Expired:</strong></p>
                <p>{user.credentialsNonExpired ? 'Yes' : 'No'}</p>
                <p><strong>Account Non-Expired:</strong></p>
                <p>{user.accountNonExpired ? 'Yes' : 'No'}</p>
                <p><strong>Account Non-Locked:</strong></p>
                <p>{user.accountNonLocked ? 'Yes' : 'No'}</p>
            </div>
        </div>
    );
}

export default ProfileDetails;