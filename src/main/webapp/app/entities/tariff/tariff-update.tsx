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
import { IBenefit } from 'app/shared/model/benefit.model';
import { getEntities as getBenefits } from 'app/entities/benefit/benefit.reducer';
import { ITariff } from 'app/shared/model/tariff.model';
import { getEntity, updateEntity, createEntity, reset } from './tariff.reducer';

export const TariffUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const currencies = useAppSelector(state => state.currency.entities);
  const benefits = useAppSelector(state => state.benefit.entities);
  const tariffEntity = useAppSelector(state => state.tariff.entity);
  const loading = useAppSelector(state => state.tariff.loading);
  const updating = useAppSelector(state => state.tariff.updating);
  const updateSuccess = useAppSelector(state => state.tariff.updateSuccess);

  const handleClose = () => {
    navigate('/tariff');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getCurrencies({}));
    dispatch(getBenefits({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...tariffEntity,
      ...values,
      currency: currencies.find(it => it.id.toString() === values.currency.toString()),
      benefit: benefits.find(it => it.id.toString() === values.benefit.toString()),
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
          ...tariffEntity,
          currency: tariffEntity?.currency?.id,
          benefit: tariffEntity?.benefit?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="medicalAidSystemApp.tariff.home.createOrEditLabel" data-cy="TariffCreateUpdateHeading">
            <Translate contentKey="medicalAidSystemApp.tariff.home.createOrEditLabel">Create or edit a Tariff</Translate>
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
                  id="tariff-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('medicalAidSystemApp.tariff.name')}
                id="tariff-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.tariff.price')}
                id="tariff-price"
                name="price"
                data-cy="price"
                type="text"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.tariff.active')}
                id="tariff-active"
                name="active"
                data-cy="active"
                check
                type="checkbox"
              />
              <ValidatedField
                id="tariff-currency"
                name="currency"
                data-cy="currency"
                label={translate('medicalAidSystemApp.tariff.currency')}
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
                id="tariff-benefit"
                name="benefit"
                data-cy="benefit"
                label={translate('medicalAidSystemApp.tariff.benefit')}
                type="select"
              >
                <option value="" key="0" />
                {benefits
                  ? benefits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tariff" replace color="info">
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

export default TariffUpdate;
