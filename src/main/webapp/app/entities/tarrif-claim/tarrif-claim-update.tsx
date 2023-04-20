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
import { IClaim } from 'app/shared/model/claim.model';
import { getEntities as getClaims } from 'app/entities/claim/claim.reducer';
import { ITarrifClaim } from 'app/shared/model/tarrif-claim.model';
import { getEntity, updateEntity, createEntity, reset } from './tarrif-claim.reducer';

export const TarrifClaimUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const currencies = useAppSelector(state => state.currency.entities);
  const claims = useAppSelector(state => state.claim.entities);
  const tarrifClaimEntity = useAppSelector(state => state.tarrifClaim.entity);
  const loading = useAppSelector(state => state.tarrifClaim.loading);
  const updating = useAppSelector(state => state.tarrifClaim.updating);
  const updateSuccess = useAppSelector(state => state.tarrifClaim.updateSuccess);

  const handleClose = () => {
    navigate('/tarrif-claim');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getCurrencies({}));
    dispatch(getClaims({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...tarrifClaimEntity,
      ...values,
      currency: currencies.find(it => it.id.toString() === values.currency.toString()),
      claim: claims.find(it => it.id.toString() === values.claim.toString()),
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
          ...tarrifClaimEntity,
          currency: tarrifClaimEntity?.currency?.id,
          claim: tarrifClaimEntity?.claim?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="medicalAidSystemApp.tarrifClaim.home.createOrEditLabel" data-cy="TarrifClaimCreateUpdateHeading">
            <Translate contentKey="medicalAidSystemApp.tarrifClaim.home.createOrEditLabel">Create or edit a TarrifClaim</Translate>
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
                  id="tarrif-claim-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('medicalAidSystemApp.tarrifClaim.tarrifCode')}
                id="tarrif-claim-tarrifCode"
                name="tarrifCode"
                data-cy="tarrifCode"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.tarrifClaim.quantity')}
                id="tarrif-claim-quantity"
                name="quantity"
                data-cy="quantity"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.tarrifClaim.amount')}
                id="tarrif-claim-amount"
                name="amount"
                data-cy="amount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.tarrifClaim.description')}
                id="tarrif-claim-description"
                name="description"
                data-cy="description"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="tarrif-claim-currency"
                name="currency"
                data-cy="currency"
                label={translate('medicalAidSystemApp.tarrifClaim.currency')}
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
              <ValidatedField
                id="tarrif-claim-claim"
                name="claim"
                data-cy="claim"
                label={translate('medicalAidSystemApp.tarrifClaim.claim')}
                type="select"
              >
                <option value="" key="0" />
                {claims
                  ? claims.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tarrif-claim" replace color="info">
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

export default TarrifClaimUpdate;
