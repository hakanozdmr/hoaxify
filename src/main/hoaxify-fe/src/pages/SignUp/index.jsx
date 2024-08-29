import axios from "axios";
import { useEffect, useMemo, useState } from "react";
import { signUp } from "./api";
import { Input } from "./components/Input";
import { useTranslation } from "react-i18next";
import { LanguageSelector } from "../../shared/components/LanguageSelector";

export function SignUp() {
  const [username, setUsername] = useState();
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [passwordRepeat, setPasswordRepeat] = useState();
  const [apiProgress, setApiProgress] = useState(false);
  const [successMessage, setSuccesMessage] = useState();
  const [errors, setErrors] = useState([]);
  const [generalError, setGeneralError] = useState();

  const{t} = useTranslation();
  useEffect(() => {
    setErrors(function (lastErrors) {
      return {
        ...lastErrors,
        username: undefined,
      };
    });
  }, [username]);

  useEffect(() => {
    setErrors(function (lastErrors) {
      return {
        ...lastErrors,
        email: undefined,
      };
    });
  }, [email]);

  useEffect(() => {
    setErrors(function (lastErrors) {
      return {
        ...lastErrors,
        password: undefined,
      };
    });
  }, [password]);

  const onSubmit = async (event) => {
    event.preventDefault();
    setSuccesMessage();
    setGeneralError();
    setApiProgress(true);
    try {
      const response = await signUp({
        username,
        email,
        password,
      });
      setSuccesMessage(response.data.message);
    } catch (axiosError) {
      if (
        axiosError.response?.data &&
        axiosError.response.data.status === 400
      ) {
        setErrors(axiosError.response.data.validationErrors);
      } else {
        setGeneralError(t('generalError'));
      }
    } finally {
      setApiProgress(false);
    }
  };

  let passwordRepeatError = useMemo(() => {
    if (password && password !== passwordRepeat) {
      return t('passwordMissmatch');
    }
    return '';
  }, [password, passwordRepeat]);

  return (
    <div className="container">
      <div className="col-lg-6 offset-lg-3">
        <form onSubmit={onSubmit} className="card mt-3">
          <div className="text-center card-header">
            <h1>{t('signUp')}</h1>
          </div>
          <div className="card-body">
            <Input
              id="username"
              label={t('username')}
              error={errors.username}
              onChange={(event) => setUsername(event.target.value)}
              type="text"
            />
            <Input
              id="email"
              label={t('email')}
              error={errors.email}
              onChange={(event) => setEmail(event.target.value)}
              type="text"
            />
            <Input
              id="password"
              label={t('password')}
              error={errors.password}
              onChange={(event) => setPassword(event.target.value)}
              type="password"
            />
            <Input
              id="passwordRepeat"
              label={t('passwordRepeat')}
              error={passwordRepeatError}
              onChange={(event) => setPasswordRepeat(event.target.value)}
              type="password"
            />
          </div>
          {successMessage && (
            <div className="alert alert-success m-3">{successMessage}</div>
          )}
          {generalError && (
            <div className="alert alert-danger m-3 mt-0">{generalError}</div>
          )}
          <div className="text-center mb-3">
            <button
              className="btn btn-primary"
              disabled={!password || password !== passwordRepeat || apiProgress}
            >
              {apiProgress && (
                <span
                  className="spinner-border spinner-border-sm"
                  aria-hidden="true"
                ></span>
              )}
              {t('signUp')}
            </button>
          </div>
        </form>
        <LanguageSelector></LanguageSelector>
      </div>
    </div>
  );
}
