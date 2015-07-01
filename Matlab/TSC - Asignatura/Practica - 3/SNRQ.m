function y = SNRQ(entrada,error)
%La SNRQ se define como la relacion senal-ruido de cuantificacion
% y es una medida indicativa de la bondad de la cuantificacion.
% Se define como la razon entre la varianza de la senal de entrada y la
% varianza de la senal error de la cuantificacion
varianza_signal = var(entrada);
varianza_error = var(error);

y = 10 * log(varianza_signal/varianza_error) / log(10);
