from firebase_functions import https_fn
from firebase_admin import initialize_app
from firebase_admin import auth
from typing import Any

initialize_app()


@https_fn.on_call()
def getUserDisplayName(req: https_fn.CallableRequest) -> Any:
    uid = req.data.get('uid', None)
    print(uid)
    if (uid is None) or (uid == ''):
        raise https_fn.HttpsError(code=https_fs.FunctionsErrorCode.INVALID_ARGUMENT,
                                  message=('No user ID passed'))
    print(uid)
    displayName = auth.get_user(uid).display_name
    print(displayName)
    return displayName
