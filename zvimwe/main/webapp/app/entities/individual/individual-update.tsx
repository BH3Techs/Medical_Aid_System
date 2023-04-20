import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContactDetails } from 'app/shared/model/contact-details.model';
import { getEntities as getContactDetails } from 'app/entities/contact-details/contact-details.reducer';
import { IBankingDetails } from 'app/shared/model/banking-details.model';
import { getEntities as getBankingDetails } from 'app/entities/banking-details/banking-details.reducer';
import { IRiskProfile } from 'app/shared/model/risk-profile.model';
import { getEntities as getRiskProfiles } from 'app/entities/risk-profile/risk-profile.reducer';
import { IIndividual } from 'app/shared/model/individual.model';
import { getEntity, updateEntity, createEntity, reset } from './individual.reducer';

export const IndividualUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const contactDetails = useAppSelector(state => state.contactDetails.entities);
  const bankingDetails = useAppSelector(state => state.bankingDetails.entities);
  const riskProfiles = useAppSelector(state => state.riskProfile.entities);
  const individualEntity = useAppSelector(state => state.individual.entity);
  const loading = useAppSelector(state => state.individual.loading);
  const updating = useAppSelector(state => state.individual.updating);
  const updateSuccess = useAppSelector(state => state.individual.updateSuccess);

  const handleClose = () => {
    navigate('/individual');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getContactDetails({}));
    dispatch(getBankingDetails({}));
    dispatch(getRiskProfiles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...individualEntity,
      ...values,
      contactDetails: contactDetails.find(it => it.id.toString() === values.contactDetails.toString()),
      bankingDetails: bankingDetails.find(it => it.id.toString() === values.bankingDetails.toString()),
      riskProfile: riskProfiles.find(it => it.id.toString() === values.riskProfile.toString()),
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
          ...individualEntity,
          contactDetails: individualEntity?.contactDetails?.id,
          bankingDetails: individualEntity?.bankingDetails?.id,
          riskProfile: individualEntity?.riskProfile?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="medicalAidSystemApp.individual.home.createOrEditLabel" data-cy="IndividualCreateUpdateHeading">
            <Translate contentKey="medicalAidSystemApp.individual.home.createOrEditLabel">Create or edit a Individual</Translate>
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
                  id="individual-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('medicalAidSystemApp.individual.firstName')}
                id="individual-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.individual.lastName')}
                id="individual-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.individual.initial')}
                id="individual-initial"
                name="initial"
                data-cy="initial"
                type="text"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.individual.dateOfBirth')}
                id="individual-dateOfBirth"
                name="dateOfBirth"
                data-cy="dateOfBirth"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.individual.gender')}
                id="individual-gender"
                name="gender"
                data-cy="gender"
                type="text"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.individual.nationalId')}
                id="individual-nationalId"
                name="nationalId"
                data-cy="nationalId"
                type="text"
                validate={{
                  pattern: {
                    value: /^\d{2}-[0-9]{5}[\w]\d{2}$/,
                    message: translate('entity.validation.pattern', { pattern: '^\\d{2}-[0-9]{5}[\\w]\\d{2}$' }),
                  },
                }}
              />
              <ValidatedField
                id="individual-contactDetails"
                name="contactDetails"
                data-cy="contactDetails"
                label={translate('medicalAidSystemApp.individual.contactDetails')}
                type="select"
              >
                <option value="" key="0" />
                {contactDetails
                  ? contactDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="individual-bankingDetails"
                name="bankingDetails"
                data-cy="bankingDetails"
                label={translate('medicalAidSystemApp.individual.bankingDetails')}
                type="select"
              >
                <option value="" key="0" />
                {bankingDetails
                  ? bankingDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="individual-riskProfile"
                name="riskProfile"
                data-cy="riskProfile"
                label={translate('medicalAidSystemApp.individual.riskProfile')}
                type="select"
              >
                <option value="" key="0" />
                {riskProfiles
                  ? riskProfiles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/individual" replace color="info">
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

export default IndividualUpdate;
