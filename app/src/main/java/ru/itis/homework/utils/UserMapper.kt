package ru.itis.homework.utils

import ru.itis.homework.data.db.entity.UserEntity
import ru.itis.homework.model.UserModel

object UserMapper {
    fun userEntityFromUserModel(userModel: UserModel) = UserEntity(
        name = userModel.name,
        phoneNumber = userModel.phoneNumber,
        emailAddress = userModel.emailAddress,
        password = PasswordEncoder.encoder(userModel.password)
    )

    fun userModelFromUserEntity(userEntity: UserEntity) =
            UserModel(
                id = userEntity.id,
                name = userEntity.name,
                phoneNumber = userEntity.phoneNumber,
                emailAddress = userEntity.emailAddress,
                password = userEntity.password, deleteDate = userEntity.deletionDate
            )


}