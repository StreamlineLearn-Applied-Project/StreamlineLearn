import React from 'react';
import './styles.css';

function ProfileDetails({ user }) {
    return (
        <div class="main-body">
            <div class="row gutters-sm">
                <div class="col-md-4 mb-3">
                    <div class="card">
                        <div class="card-body">
                            <div class="d-flex flex-column align-items-center text-center">
                                <img src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="Admin" class="rounded-circle" width="150"/>
                                <div class="mt-3">
                                    <h4>{user.firstName} {user.lastName}</h4>
                                    <p class="text-secondary mb-1">{user.role}</p>
                                    <p class="text-muted font-size-sm">Bay Area, San Francisco, CA</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-8">
                    <div class="card mb-3">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-sm-5">
                                    <h6 class="mb-0">User ID:</h6>
                                </div>
                                <div class="col-sm-7 text-secondary">
                                    {user.id}
                                </div>
                            </div> 
                            <div class="row">
                                <div class="col-sm-5">
                                    <h6 class="mb-0">User Full Name:</h6>
                                </div>
                                <div class="col-sm-7 text-secondary">
                                    {user.firstName} {user.lastName}
                                </div>
                            </div> 
                            <div class="row">
                                <div class="col-sm-5">
                                    <h6 class="mb-0">User Name:</h6>
                                </div>
                                <div class="col-sm-7 text-secondary">
                                    {user.username}
                                </div>
                            </div>   
                            <div class="row">
                                <div class="col-sm-5">
                                    <h6 class="mb-0">User Role:</h6>
                                </div>
                                <div class="col-sm-7 text-secondary">
                                    {user.role}
                                </div>
                            </div>  
                            <div class="row">
                                <div class="col-sm-5">
                                    <h6 class="mb-0">Enabled:</h6>
                                </div>
                                <div class="col-sm-7 text-secondary">
                                    {user.enabled ? 'Yes' : 'No'}
                                </div>
                            </div> 
                            <div class="row">
                                <div class="col-sm-5">
                                    <h6 class="mb-0">Authorities:</h6>
                                </div>
                                <div class="col-sm-7 text-secondary">
                                    {user.authorities.map(auth => auth.authority).join(', ')}
                                </div>
                            </div> 
                            <div class="row">
                                <div class="col-sm-5">
                                    <h6 class="mb-0">Credentials Non-Expired:</h6>
                                </div>
                                <div class="col-sm-7 text-secondary">
                                    {user.credentialsNonExpired ? 'Yes' : 'No'}
                                </div>
                            </div> 
                            <div class="row">
                                <div class="col-sm-5">
                                    <h6 class="mb-0">Account Non-Expired:</h6>
                                </div>
                                <div class="col-sm-7 text-secondary">
                                    {user.accountNonExpired ? 'Yes' : 'No'}
                                </div>
                            </div> 
                            <div class="row">
                                <div class="col-sm-5">
                                    <h6 class="mb-0">Account Non-Locked:</h6>
                                </div>
                                <div class="col-sm-7 text-secondary">
                                    {user.accountNonLocked ? 'Yes' : 'No'}
                                </div>
                            </div> 
                        </div>
                    </div>
                </div>
            </div>
            
            </div>
               
    );
}

export default ProfileDetails;