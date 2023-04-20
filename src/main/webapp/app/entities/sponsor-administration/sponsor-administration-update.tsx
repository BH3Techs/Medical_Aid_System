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
import { ISponsorAdministration } from 'app/shared/model/sponsor-administration.model';
import { SponsorType } from 'app/shared/model/enumerations/sponsor-type.model';
import { getEntity, updateEntity, createEntity, reset } from './sponsor-administration.reducer';

export const SponsorAdministrationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const contactDetails = useAppSelector(state => state.contactDetails.entities);
  const sponsorAdministrationEntity = useAppSelector(state => state.sponsorAdministration.entity);
  const loading = useAppSelector(state => state.sponsorAdministration.loading);
  const updating = useAppSelector(state => state.sponsorAdministration.updating);
  const updateSuccess = useAppSelector(state => state.sponsorAdministration.updateSuccess);
  const sponsorTypeValues = Object.keys(SponsorType);

  const handleClose = () => {
    navigate('/sponsor-administration');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getContactDetails({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...sponsorAdministrationEntity,
      ...values,
      contactDetails: contactDetails.find(it => it.id.toString() === values.contactDetails.toString()),
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
          sponsorType: 'INDIVIDUAL',
          ...sponsorAdministrationEntity,
          contactDetails: sponsorAdministrationEntity?.contactDetails?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="medicalAidSystemApp.sponsorAdministration.home.createOrEditLabel" data-cy="SponsorAdministrationCreateUpdateHeading">
            <Translate contentKey="medicalAidSystemApp.sponsorAdministration.home.createOrEditLabel">
              Create or edit a SponsorAdministration
            </Translate>
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
                  id="sponsor-administration-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('medicalAidSystemApp.sponsorAdministration.firstName')}
                id="sponsor-administration-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.sponsorAdministration.lastName')}
                id="sponsor-administration-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.sponsorAdministration.initial')}
                id="sponsor-administration-initial"
                name="initial"
                data-cy="initial"
                type="text"
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.sponsorAdministration.dateOfBirth')}
                id="sponsor-administration-dateOfBirth"
                name="dateOfBirth"
                data-cy="dateOfBirth"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.sponsorAdministration.sponsorId')}
                id="sponsor-administration-sponsorId"
                name="sponsorId"
                data-cy="sponsorId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('medicalAidSystemApp.sponsorAdministration.sponsorType')}
                id="sponsor-administration-sponsorType"
                name="sponsorType"
                data-cy="sponsorType"
                type="select"
              >
                {sponsorTypeValues.map(sponsorType => (
                  <option value={sponsorType} key={sponsorType}>
                    {translate('medicalAidSystemApp.SponsorType.' + sponsorType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="sponsor-administration-contactDetails"
                name="contactDetails"
                data-cy="contactDetails"
                label={translate('medicalAidSystemApp.sponsorAdministration.contactDetails')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sponsor-administration" replace color="info">
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

export default SponsorAdministrationUpdate;
