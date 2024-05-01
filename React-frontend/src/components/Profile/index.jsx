import React from 'react';

function ProfileDetails({ user }) {
    return (
        <div>
            <h1>Profile Details</h1>
            <p><strong>ID:</strong> {user.id}</p>
            <p><strong>First Name:</strong> {user.firstName}</p>
            <p><strong>Last Name:</strong> {user.lastName}</p>
            <p><strong>Username:</strong> {user.username}</p>
            <p><strong>Role:</strong> {user.role}</p>
            <p><strong>Enabled:</strong> {user.enabled ? 'Yes' : 'No'}</p>
            <p><strong>Authorities:</strong> {user.authorities.map(auth => auth.authority).join(', ')}</p>
            <p><strong>Credentials Non-Expired:</strong> {user.credentialsNonExpired ? 'Yes' : 'No'}</p>
            <p><strong>Account Non-Expired:</strong> {user.accountNonExpired ? 'Yes' : 'No'}</p>
            <p><strong>Account Non-Locked:</strong> {user.accountNonLocked ? 'Yes' : 'No'}</p>
        </div>
    );
}

export default ProfileDetails;