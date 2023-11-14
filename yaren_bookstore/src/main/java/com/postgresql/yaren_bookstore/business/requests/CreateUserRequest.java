package com.postgresql.yaren_bookstore.business.requests;

import com.oredata.bookStore.common.utilities.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    String email;
    UserRole role; // USER or ADMIN, Rolün buradan gönderilmesi normalde doğru değil farklı şekilde ele alınmalı.
    String name;
    String password;
}
