import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICurrency } from 'app/shared/model/currency.model';
import { getEntities as getCurrencies } from 'app/entities/currency/currency.reducer';
import { IWallet } from 'app/shared/model/wallet.model';
import { WalletOwnerType } from 'app/shared/model/enumerations/wallet-owner-type.model';
import { getEntity, updateEntity, createEntity, reset } from './wallet.reducer';

export const WalletUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const currencies = useAppSelector(state => state.currency.entities);
  const walletEntity = useAppSelector(state => state.wallet.entity);
  const loading = useAppSelector(state => state.wallet.loading);
  const updating = useAppSelector(state => state.wallet.updating);
  const updateSuccess = useAppSelector(state => state.wallet.updateSuccess);
  const walletOwnerTypeValues = Object.keys(WalletOwnerType);

  const handleClose = () => {
    navigate('/wallet');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getCurrencies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...walletEntity,
      ...values,
      currency: currencies.find(it => it.id.toString() === values.currency.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ownerType: 'POLICY_SPONSOR',
          ...walletEntity,
          currency: walletEntity?.currency?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="medicalAidSystemApp.wallet.home.createOrEditLabel" data-cy="WalletCreateUpdateHeading">
            <Translate contentKey="medicalAidSystemApp.wallet.home.createOrEditLabel">Create or edit a Wallet</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="wallet-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('medicalAidSystemApp.wallet.name')}
                id="wallet-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.wallet.balance')}
                id="wallet-balance"
                name="balance"
                data-cy="balance"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.wallet.ownerIdentifier')}
                id="wallet-ownerIdentifier"
                name="ownerIdentifier"
                data-cy="ownerIdentifier"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.wallet.ownerType')}
                id="wallet-ownerType"
                name="ownerType"
                data-cy="ownerType"
                type="select"
              >
                {walletOwnerTypeValues.map(walletOwnerType => (
                  <option value={walletOwnerType} key={walletOwnerType}>
                    {translate('medicalAidSystemApp.WalletOwnerType.' + walletOwnerType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('medicalAidSystemApp.wallet.description')}
                id="wallet-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.wallet.active')}
                id="wallet-active"
                name="active"
                data-cy="active"
                check
                type="checkbox"
              />
              <ValidatedField
                id="wallet-currency"
                name="currency"
                data-cy="currency"
                label={translate('medicalAidSystemApp.wallet.currency')}
                type="select"
              >
                <option value="" key="0" />
                {currencies
                  ? currencies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/wallet" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default WalletUpdate;
